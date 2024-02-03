package com.example.platform.repository;

import com.example.platform.model.SaleAnnouncement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleAnnouncementRepository extends JpaRepository<SaleAnnouncement, Long> {
}
