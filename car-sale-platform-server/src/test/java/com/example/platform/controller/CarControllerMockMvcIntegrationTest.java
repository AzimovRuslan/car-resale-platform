package com.example.platform.controller;

import com.example.platform.AbstractMvcTest;
import com.example.platform.mapper.CarMapper;
import com.example.platform.model.Car;
import com.example.platform.repository.CarRepository;
import com.example.platform.service.CarService;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@NoArgsConstructor
class CarControllerMockMvcIntegrationTest extends AbstractMvcTest {
    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarMapper carMapper;

    private Car car;

    @BeforeEach
    void init() {

        carRepository.deleteAll();

        car = new Car();
        car.setBrand("BMW");
        car.setModel("X5");
        car.setGeneration("E39");
        car.setYear(2003);
    }

    @Test
    void givenCar_whenAdd_thenStatus201andCarReturned() throws Exception {
        final String token = extractToken(login("Admin", "admin").andReturn());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/cars")
                        .content(objectMapper.writeValueAsString(car))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("BMW"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("X5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.generation").value("E39"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year").value(2003));
    }

    @Test
    void givenId_whenGetExistingCar_thenStatus200andCarReturned() throws Exception {

        final String token = extractToken(login("Admin", "admin").andReturn());
        long id = createTestCar("Audi", "A6", "C8", 2018).getId();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/cars/{id}", id)
                        .header("Authorization", "Bearer " + token)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("Audi"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("A6"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.generation").value("C8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year").value(2018));
    }

    @Test
    void giveCar_whenUpdate_thenStatus200andUpdatedReturns() throws Exception {

        final String token = extractToken(login("Admin", "admin").andReturn());
        long id = createTestCar("Audi", "A6", "C8", 2018).getId();

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/cars/{id}", id)
                        .content(objectMapper.writeValueAsString(car))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("BMW"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("X5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.generation").value("E39"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year").value(2003));
    }

    @Test
    void givenCar_whenDeleteCar_thenStatus200() throws Exception {

        final String token = extractToken(login("Admin", "admin").andReturn());
        Car testCar = createTestCar("Audi", "A6", "C8", 2018);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/cars/{id}", testCar.getId())
                        .header("Authorization", "Bearer " + token)
        )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void givenCars_whenGetCars_thenStatus200() throws Exception {

        final String token = extractToken(login("Admin", "admin").andReturn());
        createTestCar("Audi", "A6", "C8", 2018);
        createTestCar("Audi", "A6", "C6", 2008);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/cars/")
                        .header("Authorization", "Bearer " + token)
        )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private Car createTestCar(String brand, String model, String generation, int year) {

        Car testCar = new Car();
        testCar.setBrand(brand);
        testCar.setModel(model);
        testCar.setGeneration(generation);
        testCar.setYear(year);

        return carService.create(carMapper.toDto(testCar));
    }
}
