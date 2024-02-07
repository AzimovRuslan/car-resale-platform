package com.example.platform.controller;

import com.example.platform.dto.SaleAnnouncementDTO;
import com.example.platform.model.SaleAnnouncement;
import com.example.platform.service.SaleAnnouncementService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sale-announcements")
@AllArgsConstructor
public class SaleAnnouncementController {
    private final SaleAnnouncementService saleAnnouncementService;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('USER')")
    public List<SaleAnnouncement> getAll() {
        return saleAnnouncementService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public SaleAnnouncement get(@PathVariable("id") Long id) {
        return saleAnnouncementService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public SaleAnnouncement create(@RequestBody SaleAnnouncementDTO saleAnnouncementDTO) {
        return saleAnnouncementService.create(saleAnnouncementDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SaleAnnouncement update(@PathVariable("id") Long id, @RequestBody SaleAnnouncementDTO saleAnnouncementDTO) {
        return saleAnnouncementService.update(id, saleAnnouncementDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable("id") Long id) {
        saleAnnouncementService.deleteById(id);
    }
}
