package com.example.demo.service;

import com.example.demo.dto.mapper.UserMapper;
import com.example.demo.dto.user.teacher.CreateTeacherRequest;
import com.example.demo.dto.user.teacher.UpdateTeacherRequest;
import com.example.demo.model.Teacher;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.utils.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeacherService extends BaseService {

  private final UserMapper userMapper;
  private final IdGenerator idGenerator;
  private final TeacherRepository teacherRepository;

  @Transactional
  public Teacher createTeacher(CreateTeacherRequest request) {
    Teacher teacher = userMapper.fromRequest(request);
    teacher.setRegistrationNumber(idGenerator.generateUserId());
    teacher.setSchool(currentSchool());
    persistUsedEmail(teacher.getEmail());
    persistUsedPhoneNumber(teacher.getPhoneNumber());
    return teacherRepository.save(teacher);
  }

  @Transactional
  public Teacher updateTeacher(Teacher teacher, UpdateTeacherRequest request) {
    return processUserUpdate(
        teacher,
        request,
        Teacher::getEmail,
        Teacher::getPhoneNumber,
        UpdateTeacherRequest::getEmail,
        UpdateTeacherRequest::getPhoneNumber,
        userMapper::updateFromRequest,
        teacherRepository::save);
  }

  public Page<Teacher> findTeachers(Pageable pageable) {
    return teacherRepository.findAll(pageable);
  }

  @Transactional
  public void deleteTeacher(Teacher teacher) {
    deleteEmailAndPhone(teacher.getEmail(), teacher.getPhoneNumber());
    teacherRepository.delete(teacher);
  }
}
