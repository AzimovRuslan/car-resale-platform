package com.example.platform.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class CarDTO {

    private Long id;
    private String brand;
    private String model;
    private String generation;
    private int year;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDTO carDTO = (CarDTO) o;
        return year == carDTO.year && Objects.equals(brand, carDTO.brand) && Objects.equals(model, carDTO.model) && Objects.equals(generation, carDTO.generation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, model, generation, year);
    }
}
