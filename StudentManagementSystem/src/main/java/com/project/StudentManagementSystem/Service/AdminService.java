package com.project.StudentManagementSystem.Service;

import com.project.StudentManagementSystem.DTO.Admin.AdminLoginOtpRequest;
import com.project.StudentManagementSystem.DTO.Admin.AdminLoginRequest;
import com.project.StudentManagementSystem.DTO.Admin.AdminLoginVerifyOtp;
import com.project.StudentManagementSystem.DTO.Admin.AdminRegisterRequest;
import com.project.StudentManagementSystem.DTO.Admin.Dep_course.NewCourseRequest;
import com.project.StudentManagementSystem.DTO.Admin.Dep_course.NewDepartmentRequest;
import com.project.StudentManagementSystem.DTO.Admin.Dep_course.UpdateDepartment;
import com.project.StudentManagementSystem.Entity.*;
import com.project.StudentManagementSystem.Respository.AdminRepository;
import com.project.StudentManagementSystem.Respository.CourseRepository;
import com.project.StudentManagementSystem.Respository.DepartmentRepository;
import com.project.StudentManagementSystem.Respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CourseRepository courseRepository;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public ResponseEntity<?> register(AdminRegisterRequest request) {
        if (adminRepository.existsByUsername(request.getUsername())) {
            return new ResponseEntity<>("Username already exist!", HttpStatus.CONFLICT);
        }
        if (adminRepository.existsByEmail(request.getEmail())) {
            return new ResponseEntity<>("Email already exist!", HttpStatus.CONFLICT);
        }
        Admin admin = new Admin();
        admin.setEmail(request.getEmail());
        admin.setUsername(request.getUsername());
        String  encryptPassword = encoder.encode(request.getPassword());
        admin.setPassword(encryptPassword);
        admin.setCreatedAt(Instant.now());
        adminRepository.save(admin);
        return new ResponseEntity<>("Admin register successfully", HttpStatus.ACCEPTED);
    }

    public ResponseEntity<?> login(AdminLoginRequest loginRequest) {
        if (!adminRepository.existsByUsername(loginRequest.getUsername())){
            return new ResponseEntity<>("user name not exists!",HttpStatus.BAD_REQUEST);
        }
        Admin ad = adminRepository.findByUsername(loginRequest.getUsername());
        if (!encoder.matches(loginRequest.getPassword(), ad.getPassword())){
            return new ResponseEntity<>("Password incorrect!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("login successful",HttpStatus.ACCEPTED);
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // 6-digit
        return String.valueOf(otp);
    }

    public ResponseEntity<?> sendOtp(AdminLoginOtpRequest request) {
        if(!adminRepository.existsByEmail(request.getEmail())){
            return new ResponseEntity<>("This email is not exists!", HttpStatus.BAD_REQUEST);
        }
        String otp =generateOtp();
        emailService.sendOtp(request.getEmail(),otp);
        Admin admin = adminRepository.findByEmail(request.getEmail());
        admin.setLoginOtp(otp);
        adminRepository.save(admin);
        return new ResponseEntity<>("OTP send successfully!", HttpStatus.ACCEPTED);
    }

    public ResponseEntity<?> verifyOtp(AdminLoginVerifyOtp request) {
        if(!adminRepository.existsByEmail(request.getEmail())){
            return new ResponseEntity<>("This email is not exists!", HttpStatus.BAD_REQUEST);
        }
        Admin admin = adminRepository.findByEmail(request.getEmail());
        if(!admin.getLoginOtp().equals(request.getOtp())){
            return new ResponseEntity<>("OTP is Invalid!", HttpStatus.BAD_REQUEST);
        }
        admin.setLoginOtp(null);
        adminRepository.save(admin);
        return new ResponseEntity<>("OTP verified Successful!", HttpStatus.ACCEPTED);
    }

    public List<Users> userDetails(Long adminId) {
        checkAdmin(adminId);
        List<Users> users = userRepository.findAllByStatus(Status.PENDING);
        return users;
    }

    public ResponseEntity<?> userStatusUpdate(Long userId,Long adminId, Status status) {
        checkAdmin(adminId);
        if(!userRepository.existsById(userId)){
            return new ResponseEntity<>("This Id is not exists!", HttpStatus.BAD_REQUEST);
        }
        Optional<Users> user = userRepository.findById(userId);
        Users u = user.get();
        u.setStatus(status);
        userRepository.save(u);
        return new ResponseEntity<>("Status successfully updated!", HttpStatus.ACCEPTED);
    }
    public void checkAdmin(Long adminId) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new RuntimeException("User not found"));
        if(admin.getRole() != Role.ADMIN) {
            throw new RuntimeException("Access denied: Admins only");
        }
    }

    public ResponseEntity<?> addDepartment(Long adminId,NewDepartmentRequest request) {
        checkAdmin(adminId);
        if(departmentRepository.existsByName(request.getName())){
            return new ResponseEntity<>("Department Already Exist!", HttpStatus.CONFLICT);
        }
        Department department = new Department();
        department.setName(request.getName());
        department.setMaxStrength(request.getMaxStrength());
        department.setCurrentStrength(request.getCurrentStrength());
        department.setCreatedAt(Instant.now());

        departmentRepository.save(department);
        return new ResponseEntity<>("Department Added Successfully!", HttpStatus.ACCEPTED);
    }

    public ResponseEntity<?> updateDepartment(Long adminID,UpdateDepartment request) {
        checkAdmin(adminID);
        if(!departmentRepository.existsByName(request.getName())){
            return new ResponseEntity<>("Department Not Exist!", HttpStatus.BAD_REQUEST);
        }
        Department department = departmentRepository.findByName(request.getName());
        department.setCurrentStrength(request.getCurrentStrength());
        department.setMaxStrength(request.getMaxStrength());
        departmentRepository.save(department);
        return new ResponseEntity<>("Department Updated Successfully!", HttpStatus.ACCEPTED);
    }

    public ResponseEntity<?> deleteDepartment(Long adminID,Long departmentId) {
        checkAdmin(adminID);
        if(!departmentRepository.existsById(departmentId)){
            return new ResponseEntity<>("Department Not Exist!", HttpStatus.BAD_REQUEST);
        }
        departmentRepository.deleteById(departmentId);
        return new ResponseEntity<>("Department Deleted Successful!", HttpStatus.ACCEPTED);
    }

    public ResponseEntity<?> addCourse(Long adminID,NewCourseRequest request) {
        checkAdmin(adminID);
        if(courseRepository.existsByName(request.getName())){
            return new ResponseEntity<>("Course Already Exist!", HttpStatus.CONFLICT);
        }

        try{
            Department department = departmentRepository.findByName(request.getDepartmentName());
            Course course = new Course();
            course.setDepartment(department);
            course.setName(request.getName());
            course.setCreatedAt(Instant.now());
            courseRepository.save(course);
            return  new ResponseEntity<>("Course Added Successfully!", HttpStatus.ACCEPTED);

        }catch (Exception e){
            return new ResponseEntity<>("Department not present!", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> deleteCourse(Long adminID,Long courseId) {
        checkAdmin(adminID);
        if(courseRepository.existsById(courseId)){
            return new ResponseEntity<>("Course Already Exist!", HttpStatus.CONFLICT);
        }
        courseRepository.deleteById(courseId);
        return new ResponseEntity<>("Course Deleted Successfully!", HttpStatus.ACCEPTED);
    }
}

