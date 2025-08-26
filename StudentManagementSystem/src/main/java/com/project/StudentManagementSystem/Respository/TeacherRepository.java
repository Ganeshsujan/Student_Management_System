package com.project.StudentManagementSystem.Respository;

import com.project.StudentManagementSystem.Entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    boolean existsByEmail(String email);
    Teacher findByEmail(String email);
}
