// src/main/java/com/aquafarm/pond/dto/PondResponse.java
package com.aquafarm.pond.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PondResponse {

    private Long id;
    private String pondName;
    private Double sizeAcre;
    private LocalDate stockingDate;
    private Integer seedCount;
    private String prawnType;
    private String status;

    // Calculated fields
    private long cultureDay;
    private String growthStage;

    // Latest growth data
    private Double latestWeight;
    private Double survivalRate;
    private Double biomass;

    // Feed summary
    private Double totalFeedKg;
    private Double todayFeedKg;
    private Double totalFeedCost;
}