package com.example.platform.mapper;

import com.example.platform.aspect.utility.ModelMapperCreator;
import com.example.platform.dto.CarDTO;
import com.example.platform.model.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper implements Mapper<CarDTO, Car> {
    @Override
    public CarDTO toDto(Car car) {

        return ModelMapperCreator.getModelMapper().map(car, CarDTO.class);
    }

    @Override
    public Car toEntity(CarDTO carDTO) {

        return ModelMapperCreator.getModelMapper().map(carDTO, Car.class);
    }
}
