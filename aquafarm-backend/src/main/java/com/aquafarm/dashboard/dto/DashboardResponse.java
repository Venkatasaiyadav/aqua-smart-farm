// src/main/java/com/aquafarm/dashboard/dto/DashboardResponse.java
package com.aquafarm.dashboard.dto;

import com.aquafarm.pond.dto.PondResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    // Farm Overview
    private String farmName;
    private String ownerName;
    private int activePondCount;

    // Today's Summary
    private Double todayTotalFeedKg;
    private Double totalFeedConsumedKg;
    private Double totalExpense;
    private Double estimatedRevenue;

    // Pond Details
    private List<PondResponse> ponds;

    // Alerts
    private List<String> alerts;
}