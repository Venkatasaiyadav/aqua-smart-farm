// src/main/java/com/aquafarm/growth/dto/CreateGrowthRequest.java
package com.aquafarm.growth.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateGrowthRequest {

    @NotNull(message = "Pond ID is required")
    private Long pondId;

    private LocalDate sampleDate;  // defaults to today

    @NotNull(message = "Average weight is required")
    @Positive(message = "Weight must be positive")
    private Double avgWeightGrams;

    @Min(value = 1, message = "Sample count must be at least 1")
    private Integer sampleCount;

    @Min(value = 0, message = "Survival rate cannot be negative")
    @Max(value = 100, message = "Survival rate cannot exceed 100")
    private Double survivalRate;

    private String healthStatus;
    private String remarks;
}