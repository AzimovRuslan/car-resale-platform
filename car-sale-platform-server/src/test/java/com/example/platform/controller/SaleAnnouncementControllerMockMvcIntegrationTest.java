package com.example.platform.controller;

import com.example.platform.AbstractMvcTest;
import com.example.platform.mapper.CarMapper;
import com.example.platform.mapper.SaleRequestMapper;
import com.example.platform.model.Car;
import com.example.platform.model.SaleAnnouncement;
import com.example.platform.model.SaleRequest;
import com.example.platform.model.User;
import com.example.platform.repository.CarRepository;
import com.example.platform.repository.SaleAnnouncementRepository;
import com.example.platform.repository.SaleRequestRepository;
import com.example.platform.repository.UserRepository;
import com.example.platform.service.CarService;
import com.example.platform.service.SaleAnnouncementService;
import com.example.platform.service.SaleRequestService;
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
import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@NoArgsConstructor
public class SaleAnnouncementControllerMockMvcIntegrationTest extends AbstractMvcTest {
    @Autowired
    private CarService carService;

    @Autowired
    private SaleRequestService saleRequestService;

    @Autowired
    private SaleAnnouncementService saleAnnouncementService;

    @Autowired
    private SaleRequestRepository saleRequestRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private SaleRequestMapper saleRequestMapper;

    @Autowired
    private SaleAnnouncementRepository saleAnnouncementRepository;

    @Autowired
    private UserRepository userRepository;

    private Car car;
    private SaleRequest saleRequest;
    private User user;
    private SaleAnnouncement saleAnnouncement;

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
        saleRequest.setCar(carRepository.getCarByBrandAndAndModelAndGenerationAndYear(
                car.getBrand(),
                car.getModel(),
                car.getGeneration(),
                car.getYear()
        ));
        saleRequest.setDescription("This is really good car");
        saleRequest.setPrice(BigDecimal.valueOf(10000));
        saleRequestService.create(saleRequestMapper.toDto(saleRequest));

        saleAnnouncement = new SaleAnnouncement();
        saleAnnouncement.setSaleRequest(saleRequestRepository.findByDescriptionAndPrice(saleRequest.getDescription(), saleRequest.getPrice()));
    }

    @Test
    void givenSaleAnnouncement_whenAdd_thenStatus201andSaleAnnouncementReturned() throws Exception {
        final String token = extractToken(login("Admin", "admin").andReturn());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/sale-announcements")
                        .content(objectMapper.writeValueAsString(saleAnnouncement))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(saleAnnouncement.getDate().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.saleRequest.description").value("This is really good car"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.saleRequest.price").value(10000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.saleRequest.car.brand").value("BMW"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.saleRequest.car.model").value("X5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.saleRequest.car.generation").value("E39"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.saleRequest.car.year").value(2003));
    }
}
