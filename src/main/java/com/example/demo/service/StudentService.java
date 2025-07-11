package com.example.demo.service;

import com.example.demo.dto.mapper.UserMapper;
import com.example.demo.dto.user.student.CreateStudentRequest;
import com.example.demo.dto.user.student.UpdateStudentRequest;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import com.example.demo.utils.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentService extends BaseService {

  private final UserMapper userMapper;
  private final IdGenerator idGenerator;
  private final PasswordEncoder passwordEncoder;
  private final StudentRepository studentRepository;

  @Transactional
  public Student createStudent(CreateStudentRequest request) {
    Student student = userMapper.fromRequest(request);
    student.setPassword(passwordEncoder.encode(request.getPassword()));
    student.setRegistrationNumber(idGenerator.generateUserId());
    student.setSchool(currentSchool());
    persistUsedEmailAndPhone(student.getEmail(), student.getPhoneNumber());
    return studentRepository.save(student);
  }

  @Transactional
  public Student updateStudent(Student student, UpdateStudentRequest request) {
    return processUserUpdate(
        student,
        request,
        Student::getEmail,
        Student::getPhoneNumber,
        UpdateStudentRequest::getEmail,
        UpdateStudentRequest::getPhoneNumber,
        userMapper::updateFromRequest,
        studentRepository::save);
  }

  public Page<Student> findStudents(Pageable pageable) {
    return studentRepository.findAll(pageable);
  }

  @Transactional
  public void deleteStudent(Student student) {
    deleteEmailAndPhone(student.getEmail(), student.getPhoneNumber());
    studentRepository.delete(student);
  }
}
