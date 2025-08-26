package com.project.StudentManagementSystem.DTO.Teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherOptVerifyRequest {
    private String email;
    private String otp;
}
