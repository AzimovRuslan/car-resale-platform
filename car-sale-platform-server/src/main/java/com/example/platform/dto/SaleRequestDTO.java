package com.example.platform.dto;

import com.example.platform.model.Car;
import com.example.platform.model.User;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
public class SaleRequestDTO {
    private Long id;
    private User user;
    private Car car;
    private String description;
    private BigDecimal price = BigDecimal.valueOf(0.00);
    private String status = "not confirmed";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleRequestDTO that = (SaleRequestDTO) o;
        return Objects.equals(user, that.user) && Objects.equals(car, that.car) && Objects.equals(description, that.description) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, car, description, price);
    }
}
