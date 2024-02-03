package com.example.platform.service;

import com.example.platform.aspect.utility.RecordGetter;
import com.example.platform.dto.CarDTO;
import com.example.platform.dto.SaleRequestDTO;
import com.example.platform.mapper.CarMapper;
import com.example.platform.model.Car;
import com.example.platform.repository.CarRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class CarService implements Service<CarDTO> {
    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final SaleRequestService saleRequestService;

    @Override
    public List<CarDTO> findAll() {
        return carRepository.findAll().stream().map(carMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CarDTO findById(Long id) {
        return carMapper.toDto(RecordGetter.getRecordFromTable(id, carRepository));
    }

    @Override
    public CarDTO create(CarDTO carDTO) {
        final Car car = carMapper.toEntity(carDTO);
        carRepository.save(car);

        return carMapper.toDto(car);
    }

    @Override
    public CarDTO deleteById(Long id) {
        Car car = RecordGetter.getRecordFromTable(id, carRepository);

        SaleRequestDTO saleRequestDTO = saleRequestService.findAll()
                .stream()
                .filter(s -> s.getCar().equals(car))
                .findFirst()
                .orElse(null);

        if (car != null) {
            if (saleRequestDTO != null) {
                saleRequestService.deleteById(saleRequestDTO.getId());
            }
            carRepository.deleteById(id);
        }

        return carMapper.toDto(car);
    }

    @Override
    public CarDTO update(Long id, CarDTO carDTO) {
        Car car = RecordGetter.getRecordFromTable(id, carRepository);
        Car carDetails = carMapper.toEntity(carDTO);

        car.setBrand(carDetails.getBrand());
        car.setModel(carDetails.getModel());
        car.setGeneration(carDetails.getGeneration());
        car.setYear(carDetails.getYear());

        carRepository.save(car);

        return carMapper.toDto(car);
    }
}
