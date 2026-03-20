// src/main/java/com/aquafarm/auth/service/AuthService.java
package com.aquafarm.auth.service;

import com.aquafarm.auth.dto.*;
import com.aquafarm.auth.entity.OtpEntity;
import com.aquafarm.auth.repository.OtpRepository;
import com.aquafarm.config.JwtUtil;
import com.aquafarm.farm.entity.Farm;
import com.aquafarm.farm.repository.FarmRepository;
import com.aquafarm.user.entity.User;
import com.aquafarm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password
    .PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final FarmRepository farmRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;
    private final OtpService otpService;
    private final OtpRepository otpRepository;


    public void sendSms(String phoneNumber) {
        try {

            String apiKey = "YOUR_FAST2SMS_API_KEY";

            String message = "Welcome to AquaFarm Pro. Your account has been successfully registered.";

            String url = "https://www.fast2sms.com/dev/bulkV2?authorization="
                    + apiKey
                    + "&message=" + message
                    + "&language=english&route=q&numbers=" + phoneNumber;

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getForObject(url, String.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Phone number already registered");
        }
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email already registered");
            }
        }
        User user = User.builder()
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("FARMER")
                .build();

        user = userRepository.save(user);

        Farm farm = Farm.builder()
                .farmName(request.getFarmName())
                .location(request.getFarmLocation())
                .owner(user)
                .build();

        farm = farmRepository.save(farm);

        if(request.getEmail()!=null && !request.getEmail().isBlank()){
            emailService.sendWelcomeEmail(
                    request.getEmail(),
                    request.getFullName());
        }

        String token = jwtUtil.generateToken(
                user.getPhone(), user.getId(), user.getRole());

        return AuthResponse.builder()
                .token(token)
                .userId(user.getId())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .role(user.getRole())
                .farmId(farm.getId())
                .farmName(farm.getFarmName())
                .build();
    }
    public AuthResponse login(LoginRequest request) {

        // Find user
        User user = userRepository.findByPhone(request.getPhone())
                .orElseThrow(() -> new RuntimeException(
                    "Invalid phone or password"));

        // Verify password
        if (!passwordEncoder.matches(
                request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid phone or password");
        }

        // Get farm
        Farm farm = farmRepository.findByOwnerId(user.getId())
                .orElseThrow(() -> new RuntimeException(
                    "Farm not found"));

        // Generate token
        String token = jwtUtil.generateToken(
            user.getPhone(), user.getId(), user.getRole());

        return AuthResponse.builder()
                .token(token)
                .userId(user.getId())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .role(user.getRole())
                .farmId(farm.getId())
                .farmName(farm.getFarmName())
                .build();
    }

    public void sendOtp(String email){

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Email not registered. If you did not provide an email during registration, please contact the admin to reset your password."));

        String otp = otpService.generateOtp();

        otpService.saveOtp(email, otp);

        emailService.sendEmailOtp(email, otp);
    }

    public void verifyOtp(String email, String otp){

        OtpEntity entity = otpRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("OTP not found"));

        if(entity.isVerified()){
            throw new RuntimeException("OTP already used");
        }

        if(entity.getExpiryTime().isBefore(LocalDateTime.now())){
            throw new RuntimeException("OTP expired");
        }

        if(!entity.getOtp().equals(otp)){
            throw new RuntimeException("Invalid OTP");
        }

        entity.setVerified(true);
        otpRepository.save(entity);
    }

    public void resetPassword(ResetPasswordRequest request){

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Email not registered. If you did not provide an email during registration, please contact the admin to reset your password."));

        OtpEntity entity = otpRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("OTP verification required"));

        if(!entity.isVerified()){
            throw new RuntimeException("OTP not verified");
        }


        user.setPassword(
                passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

        otpRepository.delete(entity);
    }
}