package com.project.StudentManagementSystem.Respository;

import com.project.StudentManagementSystem.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {
    boolean existsByName(String name);
    Department findByName(String name);

}
