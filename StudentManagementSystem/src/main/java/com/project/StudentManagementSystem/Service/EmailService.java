package com.project.StudentManagementSystem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Generate and send OTP
    public void sendOtp(String toEmail,String otp) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your Login OTP Code");
        message.setText("Your OTP is: " + otp + " (valid for 5 minutes)");

        mailSender.send(message);
    }

}
