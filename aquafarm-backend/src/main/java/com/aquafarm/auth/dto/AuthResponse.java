// src/main/java/com/aquafarm/auth/dto/AuthResponse.java
package com.aquafarm.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private Long userId;
    private String fullName;
    private String phone;
    private String role;
    private Long farmId;
    private String farmName;
}