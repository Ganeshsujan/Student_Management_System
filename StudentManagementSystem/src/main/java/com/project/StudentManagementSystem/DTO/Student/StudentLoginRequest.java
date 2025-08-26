package com.project.StudentManagementSystem.DTO.Student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentLoginRequest {
    private String email;
    private String password;

}
