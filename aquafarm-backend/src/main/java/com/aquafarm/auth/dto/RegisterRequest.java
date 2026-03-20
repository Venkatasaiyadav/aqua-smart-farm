// src/main/java/com/aquafarm/auth/dto/RegisterRequest.java
package com.aquafarm.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 15, message = "Invalid phone number")
    private String phone;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    // Farm details (created during registration)
    @NotBlank(message = "Farm name is required")
    private String farmName;

    private String farmLocation;
    private String email;
}