package com.example.platform.dto;

import com.example.platform.model.Car;
import com.example.platform.model.User;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SaleRequestDTO {
    private User user;
    private Car car;
    private String description;
    private BigDecimal price = BigDecimal.valueOf(0.00);
}
