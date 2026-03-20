// src/main/java/com/aquafarm/growth/dto/GrowthSampleResponse.java
package com.aquafarm.growth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrowthSampleResponse {

    private Long id;
    private Long pondId;
    private String pondName;
    private LocalDate sampleDate;
    private Double avgWeightGrams;
    private Integer sampleCount;
    private Double survivalRate;
    private Double estimatedBiomass;
    private String healthStatus;
    private String remarks;

    // Calculated
    private Integer estimatedAliveCount;
    private Double recommendedDailyFeedKg;
}