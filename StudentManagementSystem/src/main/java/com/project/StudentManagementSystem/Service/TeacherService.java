package com.project.StudentManagementSystem.Service;

import com.project.StudentManagementSystem.DTO.Teacher.TeacherLoginOptRequest;
import com.project.StudentManagementSystem.DTO.Teacher.TeacherLoginRequest;
import com.project.StudentManagementSystem.DTO.Teacher.TeacherOptVerifyRequest;
import com.project.StudentManagementSystem.DTO.Teacher.TeacherRegisterRequest;
import com.project.StudentManagementSystem.Entity.*;
import com.project.StudentManagementSystem.Respository.DepartmentRepository;
import com.project.StudentManagementSystem.Respository.TeacherRepository;
import com.project.StudentManagementSystem.Respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@Service
public class TeacherService {

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;


    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // 6-digit
        return String.valueOf(otp);
    }

    public ResponseEntity<?> register(TeacherRegisterRequest request) {
        if(teacherRepository.existsByEmail(request.getEmail())){
            return new ResponseEntity<>("Email already Exists", HttpStatus.CONFLICT);
        }
        if(userRepository.existsByUsername(request.getUsername())){
            return new ResponseEntity<>("username already Exists", HttpStatus.CONFLICT);
        }
        try{

            Department department = departmentRepository.findByName(request.getDepartmentName());
            String encodePassword = encoder.encode(request.getPassword());
            Users user = new Users();
            user.setUsername(request.getUsername());
            user.setRole(Role.TEACHER);
            user.setPassword(encodePassword);
            user.setStatus(Status.PENDING);
            user.setCreatedAt(Instant.now());
            userRepository.save(user);


            Teacher teacher = new Teacher();
            teacher.setEmail(request.getEmail());
            teacher.setDepartment(department);
            teacher.setPassword(encodePassword);
            teacher.setCreatedAt(Instant.now());
            teacher.setUser(user);
            teacherRepository.save(teacher);


            return new ResponseEntity<>("Teacher Registered Successfully",HttpStatus.ACCEPTED);

        }catch (Exception e){
            return new ResponseEntity<>("Department not present!",HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> login(TeacherLoginRequest request) {
        if(!teacherRepository.existsByEmail(request.getEmail())){
            return new ResponseEntity<>("Email not exists!",HttpStatus.BAD_REQUEST);
        }
        Teacher teacher = teacherRepository.findByEmail(request.getEmail());
        if(encoder.matches(teacher.getPassword(),request.getPassword())){
            return new ResponseEntity<>("incorrect password",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Login Successfully",HttpStatus.ACCEPTED);
    }

    public ResponseEntity<?> sendOtp(TeacherLoginOptRequest request) {
        if(teacherRepository.existsByEmail(request.getEmail())){
            return new ResponseEntity<>("Email already Exists", HttpStatus.CONFLICT);
        }
        String otp = generateOtp();
        emailService.sendOtp(request.getEmail(),otp);
        Teacher teacher = teacherRepository.findByEmail(request.getEmail());
        teacher.setLoginOtp(otp);
        teacherRepository.save(teacher);
        return new ResponseEntity<>("OTP Send to your Register student mail id",HttpStatus.ACCEPTED);
    }

    public ResponseEntity<?> verifyOtp(TeacherOptVerifyRequest request) {
        if(teacherRepository.existsByEmail(request.getEmail())){
            return new ResponseEntity<>("Email already Exists", HttpStatus.CONFLICT);
        }
        Teacher teacher = teacherRepository.findByEmail(request.getEmail());
        if(!teacher.getLoginOtp().equals(request.getOtp())){
            return new ResponseEntity<>("Otp is invalid!",HttpStatus.BAD_REQUEST);
        }
        teacher.setLoginOtp(null);
        teacherRepository.save(teacher);
        return new ResponseEntity<>("Otp verified successfully",HttpStatus.ACCEPTED);
    }
}
