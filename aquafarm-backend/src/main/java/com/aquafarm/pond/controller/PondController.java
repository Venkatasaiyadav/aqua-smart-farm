// src/main/java/com/aquafarm/pond/controller/PondController.java
package com.aquafarm.pond.controller;

import com.aquafarm.common.dto.ApiResponse;
import com.aquafarm.pond.dto.CreatePondRequest;
import com.aquafarm.pond.dto.PondResponse;
import com.aquafarm.pond.service.PondService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ponds")
@RequiredArgsConstructor
public class PondController {

    private final PondService pondService;

    // POST /api/ponds?farmId=1
    @PostMapping
    public ResponseEntity<ApiResponse<PondResponse>> createPond(
            @RequestParam Long farmId,
            @Valid @RequestBody CreatePondRequest request) {

        PondResponse response =
            pondService.createPond(farmId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                    "Pond created successfully", response));
    }

    // GET /api/ponds?farmId=1
    @GetMapping
    public ResponseEntity<ApiResponse<List<PondResponse>>>
            getAllPonds(@RequestParam Long farmId) {

        List<PondResponse> ponds =
            pondService.getAllPonds(farmId);

        return ResponseEntity.ok(
            ApiResponse.success(ponds));
    }

    // GET /api/ponds/1
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PondResponse>> getPond(
            @PathVariable Long id) {

        PondResponse response = pondService.getPondById(id);
        return ResponseEntity.ok(
            ApiResponse.success(response));
    }
}