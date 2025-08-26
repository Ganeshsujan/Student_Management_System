package com.project.StudentManagementSystem.Service;

import com.project.StudentManagementSystem.DTO.Student.StudentLoginOptRequest;
import com.project.StudentManagementSystem.DTO.Student.StudentLoginRequest;
import com.project.StudentManagementSystem.DTO.Student.StudentOtpVerifyRequest;
import com.project.StudentManagementSystem.DTO.Student.StudentRegisterRequest;
import com.project.StudentManagementSystem.Entity.*;
import com.project.StudentManagementSystem.Respository.DepartmentRepository;
import com.project.StudentManagementSystem.Respository.StudentRepository;
import com.project.StudentManagementSystem.Respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.PasswordAuthentication;
import java.time.Instant;
import java.util.Optional;
import java.util.Random;


@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    EmailService emailService;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // 6-digit
        return String.valueOf(otp);
    }

    public ResponseEntity<?> register(StudentRegisterRequest request) {
        if (studentRepository.existsByEmail(request.getEmail())){
            return new ResponseEntity<>("Student already Exists", HttpStatus.CONFLICT);
        }
        if(userRepository.existsByUsername(request.getUsername())){
            return new ResponseEntity<>("username already Exists", HttpStatus.CONFLICT);
        }
        try {
            Department department = departmentRepository.findByName(request.getDepartmentName());
            if(department.getMaxStrength() > department.getCurrentStrength()){
                String encryptPasseord = encoder.encode(request.getPassword());
                Users users = new Users();
                users.setUsername(request.getUsername());
                users.setPassword(encryptPasseord);
                users.setRole(Role.STUDENT);
                users.setStatus(Status.PENDING);
                users.setCreatedAt(Instant.now());
                userRepository.save(users);

                Student student = new Student();
                student.setEmail(request.getEmail());
                student.setFullName(request.getFullName());
                student.setDepartment(department);
                student.setPassword(encryptPasseord);
                student.setCreatedAt(Instant.now());
                student.setUser(users);
                studentRepository.save(student);
                return new ResponseEntity<>("Student created Successfully", HttpStatus.ACCEPTED);
            }
        }catch (Exception e){
            return new ResponseEntity<>("Department not present!",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Something went Wrong", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> login(StudentLoginRequest request) {
        if(!studentRepository.existsByEmail(request.getEmail())){
            return new ResponseEntity<>("login not successfully",HttpStatus.BAD_REQUEST);
        }
        Student student = studentRepository.findByEmail(request.getEmail());
        if(!encoder.matches(student.getPassword(),request.getPassword())){
            return new ResponseEntity<>("password is incorrect!",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("login successfully",HttpStatus.ACCEPTED);
    }

    public ResponseEntity<?> sendOtp(StudentLoginOptRequest request) {
        if(!studentRepository.existsByEmail(request.getEmail())){
            return new ResponseEntity<>("Email not Exists",HttpStatus.BAD_REQUEST);
        }
        String otp = generateOtp();
        emailService.sendOtp(request.getEmail(),otp);
        Student student = studentRepository.findByEmail(request.getEmail());
        student.setLoginOtp(otp);
        studentRepository.save(student);
        return new ResponseEntity<>("OTP Send to your Register student mail id",HttpStatus.ACCEPTED);
    }

    public ResponseEntity<?> verifyOtp(StudentOtpVerifyRequest request) {
        if(!studentRepository.existsByEmail(request.getEmail())){
            return new ResponseEntity<>("Email not Exists",HttpStatus.BAD_REQUEST);
        }

        Student student = studentRepository.findByEmail(request.getEmail());
        if(!student.getLoginOtp().equals(request.getOtp())){
            return new ResponseEntity<>("OTP is Invalid!", HttpStatus.BAD_REQUEST);
        }
        student.setLoginOtp(null);
        studentRepository.save(student);
        return new ResponseEntity<>("OTP verified Successful!", HttpStatus.ACCEPTED);
    }
}
