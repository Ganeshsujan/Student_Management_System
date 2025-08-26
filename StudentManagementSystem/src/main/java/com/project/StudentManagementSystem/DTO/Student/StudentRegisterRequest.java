package com.project.StudentManagementSystem.DTO.Student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRegisterRequest {
    private String email;
    private String fullName;
    private String departmentName;
    private String password;
    private String username;

}
