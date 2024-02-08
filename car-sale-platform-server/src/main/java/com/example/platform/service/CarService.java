package com.example.platform.service;

import com.example.platform.aspect.utility.RecordGetter;
import com.example.platform.dto.CarDTO;
import com.example.platform.mapper.CarMapper;
import com.example.platform.model.Car;
import com.example.platform.model.SaleRequest;
import com.example.platform.repository.CarRepository;
import com.example.platform.repository.SaleRequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class CarService implements Service<Car, CarDTO> {
    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final SaleRequestService saleRequestService;
    private final SaleRequestRepository saleRequestRepository;

    @Override
    public List<Car> findAll(PageRequest pq) {
        return carRepository.findAll(pq).getContent();
    }

    @Override
    public Car findById(Long id) {
        return RecordGetter.getRecordFromTable(id, carRepository);
    }

    @Override
    public Car create(CarDTO carDTO) {
        final Car car = carMapper.toEntity(carDTO);
        carRepository.save(car);

        return car;
    }

    @Override
    public Car deleteById(Long id) {
        Car car = RecordGetter.getRecordFromTable(id, carRepository);

        SaleRequest saleRequest = saleRequestRepository.findAll()
                .stream()
                .filter(s -> s.getCar().equals(car))
                .findFirst()
                .orElse(null);

        if (car != null) {
            if (saleRequest != null) {
                saleRequestService.deleteById(saleRequest.getId());
            }
            carRepository.deleteById(id);
        }

        return car;
    }

    @Override
    public Car update(Long id, CarDTO carDTO) {
        Car car = RecordGetter.getRecordFromTable(id, carRepository);
        Car carDetails = carMapper.toEntity(carDTO);

        car.setBrand(carDetails.getBrand());
        car.setModel(carDetails.getModel());
        car.setGeneration(carDetails.getGeneration());
        car.setYear(carDetails.getYear());

        carRepository.save(car);

        return car;
    }
}
