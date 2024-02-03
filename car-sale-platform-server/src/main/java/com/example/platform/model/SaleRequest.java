package com.example.platform.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SaleRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    @OneToOne
    private Car car;

    @NotBlank(message = "Description is mandatory")
    private String description;

    private BigDecimal price = BigDecimal.valueOf(0.00);

    private String status = "not confirmed";
}
