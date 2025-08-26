package com.project.StudentManagementSystem.DTO.Teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherLoginRequest {
    private String email;
    private String password;
}
