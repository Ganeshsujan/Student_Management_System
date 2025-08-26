package com.project.StudentManagementSystem.DTO.Admin.Dep_course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDepartment {
    private String name;
    private Integer maxStrength;
    private Integer currentStrength;
}
