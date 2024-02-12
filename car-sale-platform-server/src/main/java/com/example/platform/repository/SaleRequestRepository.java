package com.example.platform.repository;

import com.example.platform.model.SaleRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SaleRequestRepository extends JpaRepository<SaleRequest, Long> {
    List<SaleRequest> findAllByStatus(String status);
    SaleRequest findByDescriptionAndPrice(String description, BigDecimal price);
}
