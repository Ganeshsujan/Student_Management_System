package com.project.StudentManagementSystem.Respository;


import com.project.StudentManagementSystem.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    boolean existsByName(String name);
}
