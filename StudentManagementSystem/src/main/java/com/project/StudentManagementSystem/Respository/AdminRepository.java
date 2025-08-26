package com.project.StudentManagementSystem.Respository;

import com.project.StudentManagementSystem.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Admin findByUsername(String usaername);

    Admin findByEmail(String email);
}
