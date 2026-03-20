// src/main/java/com/aquafarm/auth/controller/AuthController.java
package com.aquafarm.auth.controller;

import com.aquafarm.auth.dto.*;
import com.aquafarm.auth.service.AuthService;
import com.aquafarm.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;




    // POST /api/auth/register
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request)
    {

        AuthResponse response = authService.register(request);
        authService.sendSms(request.getPhone());


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                    "Registration successful", response));
    }



    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        AuthResponse response = authService.login(request);
        return ResponseEntity
                .ok(ApiResponse.success(
                    "Login successful", response));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(
            @RequestBody ForgotPasswordRequest request) {

        authService.sendOtp(request.getEmail());

        return ResponseEntity.ok(
                ApiResponse.success("OTP sent to email"));
    }


    // GET /api/health (public health check)
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> health() {
        return ResponseEntity.ok(
            ApiResponse.success("AquaFarm Pro is running! 🦐"));
    }
}