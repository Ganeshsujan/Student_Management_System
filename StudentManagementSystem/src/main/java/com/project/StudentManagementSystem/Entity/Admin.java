package com.project.StudentManagementSystem.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder
@Entity
@Table(name = "admin")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {

    @Id
    @Column(name = "admin_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "otp")
    private String loginOtp;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ADMIN;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

}
