package com.project.StudentManagementSystem.Controller;


import com.project.StudentManagementSystem.DTO.Student.*;
import com.project.StudentManagementSystem.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody StudentRegisterRequest request){
        return studentService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody StudentLoginRequest request){
        return studentService.login(request);
    }

    @PostMapping("/sendOtp")
    public ResponseEntity<?> sendOtp(@RequestBody StudentLoginOptRequest request){
        return studentService.sendOtp(request);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody StudentOtpVerifyRequest  request){
        return studentService.verifyOtp(request);
    }
}
