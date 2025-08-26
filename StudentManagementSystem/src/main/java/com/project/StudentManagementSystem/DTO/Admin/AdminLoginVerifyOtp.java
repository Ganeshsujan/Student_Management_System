package com.project.StudentManagementSystem.DTO.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginVerifyOtp {
    private String otp;
    private String email;
}
