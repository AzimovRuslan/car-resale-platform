package com.example.platform.dto;

import com.example.platform.model.SaleRequest;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class SaleAnnouncementDTO {
    private Long id;
    private SaleRequest saleRequest;
    private LocalDate date = LocalDate.now();
    private LocalTime time = LocalTime.now();
}
