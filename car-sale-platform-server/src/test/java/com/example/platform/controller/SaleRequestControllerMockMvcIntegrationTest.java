package com.example.platform.controller;

import com.example.platform.AbstractMvcTest;
import com.example.platform.mapper.CarMapper;
import com.example.platform.mapper.SaleRequestMapper;
import com.example.platform.model.*;
import com.example.platform.repository.CarRepository;
import com.example.platform.repository.SaleRequestRepository;
import com.example.platform.repository.UserRepository;
import com.example.platform.service.*;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@NoArgsConstructor
class SaleRequestControllerMockMvcIntegrationTest extends AbstractMvcTest {
    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private SaleRequestRepository saleRequestRepository;

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private SaleRequestService saleRequestService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SaleRequestMapper saleRequestMapper;

    private Car car;
    private User user;
    private SaleRequest saleRequest;

    @BeforeEach
    void init() {
        saleRequestRepository.deleteAll();
        carRepository.deleteAll();

        car = new Car();
        car.setBrand("BMW");
        car.setModel("X5");
        car.setGeneration("E39");
        car.setYear(2003);
        carService.create(carMapper.toDto(car));

        Optional<User> userOpt = userRepository.findByUsername("Admin");
        userOpt.ifPresent(value -> user = value);

        saleRequest = new SaleRequest();
        saleRequest.setUser(user);
        saleRequest.setCar(getCarFromTable());
        saleRequest.setDescription("This is really good car");
        saleRequest.setPrice(BigDecimal.valueOf(10000));
    }

    @Test
    void givenSaleRequest_whenAdd_thenStatus201andSaleRequestReturned() throws Exception {
        final String token = extractToken(login("Admin", "admin").andReturn());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/sale-requests")
                        .content(objectMapper.writeValueAsString(saleRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.car.brand").value("BMW"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.car.model").value("X5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.car.generation").value("E39"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.car.year").value(2003))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.username").value("Admin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("This is really good car"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(BigDecimal.valueOf(10000)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("not confirmed"));
    }

    @Test
    void givenId_whenGetExistingSaleRequest_thenStatus200andSaleRequestReturned() throws Exception {
        final String token = extractToken(login("Admin", "admin").andReturn());
        long id = createTestSaleRequest(user, "This is almost good car", BigDecimal.valueOf(1000)).getId();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/sale-requests/{id}", id)
                        .header("Authorization", "Bearer " + token)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.car.brand").value("BMW"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.car.model").value("X5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.car.generation").value("E39"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.car.year").value(2003))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.username").value("Admin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("This is almost good car"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(BigDecimal.valueOf(1000.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("not confirmed"));
    }

    @Test
    void giveSaleRequest_whenUpdate_thenStatus200andUpdatedReturns() throws Exception {
        final String token = extractToken(login("Admin", "admin").andReturn());
        long id = createTestSaleRequest(user, "This is bad car", BigDecimal.valueOf(1000)).getId();

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/sale-requests/{id}", id)
                        .content(objectMapper.writeValueAsString(saleRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.car.brand").value("BMW"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.car.model").value("X5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.car.generation").value("E39"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.car.year").value(2003))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.username").value("Admin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("This is really good car"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(BigDecimal.valueOf(10000)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("not confirmed"));
    }

    @Test
    void givenSaleRequest_whenDeleteSaleRequest_thenStatus200() throws Exception {
        final String token = extractToken(login("Admin", "admin").andReturn());
        SaleRequest testSaleRequest = createTestSaleRequest(user, "This is bad car", BigDecimal.valueOf(1000));

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/sale-requests/{id}", testSaleRequest.getId())
                        .header("Authorization", "Bearer " + token)
        )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private SaleRequest createTestSaleRequest(User user, String description, BigDecimal price) {
        SaleRequest saleRequest = new SaleRequest();
        saleRequest.setCar(getCarFromTable());
        saleRequest.setUser(user);
        saleRequest.setDescription(description);
        saleRequest.setPrice(price);

        return saleRequestService.create(saleRequestMapper.toDto(saleRequest));
    }

    private Car getCarFromTable() {

        return carRepository.findAll()
                .stream()
                .filter(c -> c.getBrand().equals(car.getBrand()))
                .findFirst()
                .orElse(null);
    }
}
