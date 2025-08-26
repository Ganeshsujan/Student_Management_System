package com.project.StudentManagementSystem.DTO.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginOtpRequest {
    private String email;
}
