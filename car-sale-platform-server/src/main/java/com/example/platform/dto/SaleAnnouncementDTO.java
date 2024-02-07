package com.example.platform.dto;

import com.example.platform.model.SaleRequest;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class SaleAnnouncementDTO {
    private SaleRequest saleRequest;
}
