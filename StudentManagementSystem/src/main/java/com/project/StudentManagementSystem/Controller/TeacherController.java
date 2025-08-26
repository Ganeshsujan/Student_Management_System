package com.project.StudentManagementSystem.Controller;

import com.project.StudentManagementSystem.DTO.Student.StudentLoginOptRequest;
import com.project.StudentManagementSystem.DTO.Student.StudentLoginRequest;
import com.project.StudentManagementSystem.DTO.Student.StudentOtpVerifyRequest;
import com.project.StudentManagementSystem.DTO.Student.StudentRegisterRequest;
import com.project.StudentManagementSystem.DTO.Teacher.TeacherLoginOptRequest;
import com.project.StudentManagementSystem.DTO.Teacher.TeacherLoginRequest;
import com.project.StudentManagementSystem.DTO.Teacher.TeacherOptVerifyRequest;
import com.project.StudentManagementSystem.DTO.Teacher.TeacherRegisterRequest;
import com.project.StudentManagementSystem.Service.StudentService;
import com.project.StudentManagementSystem.Service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody TeacherRegisterRequest request){
        return teacherService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody TeacherLoginRequest request){
        return teacherService.login(request);
    }

    @PostMapping("/sendOtp")
    public ResponseEntity<?> sendOtp(@RequestBody TeacherLoginOptRequest request){
        return teacherService.sendOtp(request);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody TeacherOptVerifyRequest request){
        return teacherService.verifyOtp(request);
    }
}
