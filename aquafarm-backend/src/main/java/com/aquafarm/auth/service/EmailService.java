package com.aquafarm.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendEmailOtp(String emailTo, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("venkyyadav103@gmail.com");
        message.setTo(emailTo);
        message.setSubject("AquaFarm OTP Verification");

        message.setText(
                "Your OTP for AquaFarm is: " + otp +
                        "\nThis OTP expires in 5 minutes."
        );

        javaMailSender.send(message);
    }

    public void sendWelcomeEmail(String email, String name){

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("venkyyadav103@gmail.com");
        message.setTo(email);
        message.setSubject("Welcome to AquaFarm 🦐");

        message.setText(
                "Hi " + name + ",\n\n" +
                        "Welcome to AquaFarm Pro!\n\n" +
                        "Your farm has been successfully registered.\n\n" +
                        "Happy Farming 🌊\n\n" +
                        "AquaFarm Team"
        );

        javaMailSender.send(message);
    }
}