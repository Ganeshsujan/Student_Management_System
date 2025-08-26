package com.project.StudentManagementSystem.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "teacher")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {

    @Id
    @Column(name = "teacher_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teacherId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String fullName;

    @Column(name = "otp")
    private String loginOtp;

    @Column(nullable = false)
    private String password;

    @OneToOne(optional = false)
    private Users user;

    @ManyToOne
    private Department department;

    @Column(nullable = false, name = "created_at")
    private Instant createdAt;
}
