package com.example.platform.controller;

import com.example.platform.dto.SaleRequestDTO;
import com.example.platform.model.SaleRequest;
import com.example.platform.service.SaleRequestService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sale-requests")
@AllArgsConstructor
public class SaleRequestController {
    private final SaleRequestService saleRequestService;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<SaleRequest> getAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "2") int size
    ) {
        return saleRequestService.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/status")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<SaleRequest> getAllByStatus() {
        return saleRequestService.findAllByStatus();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SaleRequest get(@PathVariable("id") Long id) {
        return saleRequestService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public SaleRequest create(@RequestBody SaleRequestDTO saleRequestDTO) {
        return saleRequestService.create(saleRequestDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public SaleRequest update(@PathVariable("id") Long id, @RequestBody SaleRequestDTO saleRequestDTO) {
        return saleRequestService.update(id, saleRequestDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public void delete(@PathVariable("id") Long id) {
        saleRequestService.deleteById(id);
    }
}
