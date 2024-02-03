package com.example.platform.controller;

import com.example.platform.dto.CarDTO;
import com.example.platform.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@AllArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<CarDTO> getAll() {
        return carService.findAll();
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public CarDTO get(@PathVariable("id") Long id) {
        return carService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public CarDTO create(@RequestBody CarDTO carDTO) {
        return carService.create(carDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public CarDTO update(@PathVariable("id") Long id, @RequestBody CarDTO carDTO) {
        return carService.update(id, carDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public void delete(@PathVariable("id") Long id) {
        carService.deleteById(id);
    }
}
