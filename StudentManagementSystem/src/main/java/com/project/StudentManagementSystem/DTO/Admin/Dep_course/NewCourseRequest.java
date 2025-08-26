package com.project.StudentManagementSystem.DTO.Admin.Dep_course;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewCourseRequest {
    private String name;
    private String departmentName;
}
