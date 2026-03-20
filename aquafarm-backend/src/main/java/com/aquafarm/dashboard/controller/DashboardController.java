// src/main/java/com/aquafarm/dashboard/controller/DashboardController.java
package com.aquafarm.dashboard.controller;

import com.aquafarm.common.dto.ApiResponse;
import com.aquafarm.dashboard.dto.DashboardResponse;
import com.aquafarm.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    // GET /api/dashboard?farmId=1
    @GetMapping
    public ResponseEntity<ApiResponse<DashboardResponse>>
            getDashboard(@RequestParam Long farmId) {

        DashboardResponse response =
            dashboardService.getDashboard(farmId);

        return ResponseEntity.ok(
            ApiResponse.success(response));
    }
}