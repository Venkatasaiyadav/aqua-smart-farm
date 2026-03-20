package com.aquafarm.auth.service;

import com.aquafarm.auth.entity.OtpEntity;
import com.aquafarm.auth.repository.OtpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final OtpRepository otpRepository;

    public String generateOtp() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }

    public void saveOtp(String email, String otp) {

        OtpEntity entity = OtpEntity.builder()
                .email(email)
                .otp(otp)
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .verified(false)
                .build();

        otpRepository.save(entity);
    }

}