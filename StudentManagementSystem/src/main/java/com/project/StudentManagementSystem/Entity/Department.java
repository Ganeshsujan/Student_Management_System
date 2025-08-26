package com.project.StudentManagementSystem.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Collate;

import java.time.Instant;

@Entity
@Table(name = "department")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    @Id
    @Column(name = "department_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Integer maxStrength;

    @Column(nullable = false)
    private Integer currentStrength;

    @Column(nullable = false, name = "created_at")
    private Instant createdAt;

}
