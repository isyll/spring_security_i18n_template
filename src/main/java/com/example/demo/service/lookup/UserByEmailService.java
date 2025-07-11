package com.example.demo.service.lookup;

import com.example.demo.model.Admin;
import com.example.demo.model.Staff;
import com.example.demo.model.Student;
import com.example.demo.model.Teacher;
import com.example.demo.model.User;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.StaffRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.SuperAdminRepository;
import com.example.demo.repository.TeacherRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserByEmailService {

  private final StudentRepository studentRepository;
  private final TeacherRepository teacherRepository;
  private final StaffRepository staffRepository;
  private final AdminRepository adminRepository;
  private final SuperAdminRepository superAdminRepository;

  public Optional<? extends User> findUserByEmail(String email) {
    Optional<Student> student = studentRepository.findByEmail(email);
    if (student.isPresent()) {
      return student;
    }

    Optional<Teacher> teacher = teacherRepository.findByEmail(email);
    if (teacher.isPresent()) {
      return teacher;
    }

    Optional<Staff> staff = staffRepository.findByEmail(email);
    if (staff.isPresent()) {
      return staff;
    }

    Optional<Admin> admin = adminRepository.findByEmail(email);
    if (admin.isPresent()) {
      return admin;
    }

    return superAdminRepository.findByEmail(email);
  }
}
