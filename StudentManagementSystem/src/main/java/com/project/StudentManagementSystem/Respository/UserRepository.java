package com.project.StudentManagementSystem.Respository;

import com.project.StudentManagementSystem.Entity.Status;
import com.project.StudentManagementSystem.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    List<Users> findAllByStatus(Status status);

    boolean existsByUsername(String username);
}
