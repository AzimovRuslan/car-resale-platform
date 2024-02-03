package com.example.platform.repository;

import com.example.platform.model.SaleRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRequestRepository extends JpaRepository<SaleRequest, Long> {
}
