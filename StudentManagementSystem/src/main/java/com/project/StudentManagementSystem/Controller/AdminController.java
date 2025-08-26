package com.project.StudentManagementSystem.Controller;


import com.project.StudentManagementSystem.DTO.Admin.AdminLoginOtpRequest;
import com.project.StudentManagementSystem.DTO.Admin.AdminLoginRequest;
import com.project.StudentManagementSystem.DTO.Admin.AdminLoginVerifyOtp;
import com.project.StudentManagementSystem.DTO.Admin.AdminRegisterRequest;
import com.project.StudentManagementSystem.DTO.Admin.Dep_course.NewCourseRequest;
import com.project.StudentManagementSystem.DTO.Admin.Dep_course.NewDepartmentRequest;
import com.project.StudentManagementSystem.DTO.Admin.Dep_course.UpdateDepartment;
import com.project.StudentManagementSystem.Entity.Status;
import com.project.StudentManagementSystem.Entity.Users;
import com.project.StudentManagementSystem.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AdminRegisterRequest request){
        return adminService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminLoginRequest loginRequest){
        return adminService.login(loginRequest);
    }

    @PostMapping("/sendOtp")
    public ResponseEntity<?> sendOtp(@RequestBody AdminLoginOtpRequest request){
        return adminService.sendOtp(request);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody AdminLoginVerifyOtp request){
        return adminService.verifyOtp(request);
    }

    @GetMapping("/user-pending-details/{adminId}")
    public List<Users> userDetails(@PathVariable Long adminId){
        return adminService.userDetails(adminId);
    }

    @PatchMapping("/user-status-change/{userId}/{adminId}/status")
    public ResponseEntity<?> userStatusUpdate(@PathVariable Long userId,@PathVariable Long adminId, @RequestParam Status status){
        return adminService.userStatusUpdate(userId,adminId,status);
    }
    //------------------------------------------------------------------
    @PostMapping("/new-department/{adminId}")
    public ResponseEntity<?> newDepartment(@RequestParam Long adminID,@RequestBody NewDepartmentRequest request){
        return adminService.addDepartment(adminID,request);
    }

    @PutMapping("/update-department/{adminId}")
    public ResponseEntity<?> updateDepartment(@RequestParam Long adminID,@RequestBody UpdateDepartment request){
        return adminService.updateDepartment(adminID,request);
    }

    @DeleteMapping("/delete-department/{departmentId}/{adminId}")
    public ResponseEntity<?> deleteDepartment(@RequestParam Long adminID,@PathVariable Long departmentId){
        return adminService.deleteDepartment(adminID,departmentId);
    }

    @PostMapping("/new-course/{adminId}")
    public ResponseEntity<?> newCourse(@RequestParam Long adminID,@RequestBody NewCourseRequest request){
        return adminService.addCourse(adminID,request);
    }


    @DeleteMapping("/delete-department/{courseId}/{adminId}")
    public ResponseEntity<?> deleteCourse(@RequestParam Long adminID,@PathVariable Long courseId){
        return adminService.deleteCourse(adminID,courseId);
    }

}
