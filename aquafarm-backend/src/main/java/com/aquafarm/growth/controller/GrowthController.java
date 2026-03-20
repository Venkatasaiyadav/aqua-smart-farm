// src/main/java/com/aquafarm/growth/controller/GrowthController.java
package com.aquafarm.growth.controller;

import com.aquafarm.common.dto.ApiResponse;
import com.aquafarm.growth.dto.CreateGrowthRequest;
import com.aquafarm.growth.dto.GrowthSampleResponse;
import com.aquafarm.growth.service.GrowthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/growth")
@RequiredArgsConstructor
public class GrowthController {

    private final GrowthService growthService;

    // POST /api/growth — Add weekly sample
    @PostMapping
    public ResponseEntity<ApiResponse<GrowthSampleResponse>>
            addSample(
                @Valid @RequestBody CreateGrowthRequest request) {

        GrowthSampleResponse response =
            growthService.addGrowthSample(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                    "Growth sample saved! 📊", response));
    }

    // GET /api/growth/pond/1 — All samples for pond
    @GetMapping("/pond/{pondId}")
    public ResponseEntity<ApiResponse<List<GrowthSampleResponse>>>
            getGrowthByPond(@PathVariable Long pondId) {

        List<GrowthSampleResponse> samples =
            growthService.getGrowthByPond(pondId);

        return ResponseEntity.ok(
            ApiResponse.success(samples));
    }

    // GET /api/growth/pond/1/latest
    @GetMapping("/pond/{pondId}/latest")
    public ResponseEntity<ApiResponse<GrowthSampleResponse>>
            getLatest(@PathVariable Long pondId) {

        GrowthSampleResponse response =
            growthService.getLatestGrowth(pondId);

        return ResponseEntity.ok(
            ApiResponse.success(response));
    }
}