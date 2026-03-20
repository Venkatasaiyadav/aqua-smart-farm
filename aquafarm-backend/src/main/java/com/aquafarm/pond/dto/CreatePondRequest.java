// src/main/java/com/aquafarm/pond/dto/CreatePondRequest.java
package com.aquafarm.pond.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreatePondRequest {

    @NotBlank(message = "Pond name is required")
    private String pondName;

    @NotNull(message = "Pond size is required")
    @Positive(message = "Pond size must be positive")
    private Double sizeAcre;

    private LocalDate stockingDate;

    @Min(value = 0, message = "Seed count cannot be negative")
    private Integer seedCount;

    private String prawnType;
}