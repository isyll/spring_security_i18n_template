package com.example.demo.service;

import com.example.demo.dto.mapper.ClassroomMapper;
import com.example.demo.dto.pagination.PaginationParams;
import com.example.demo.dto.school.ClassroomDto;
import com.example.demo.model.Classroom;
import com.example.demo.repository.ClassroomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassroomService extends BaseService {

  private final ClassroomRepository classroomRepository;
  private final ClassroomMapper classroomMapper;

  public Classroom createClassroom(ClassroomDto classroomDto) {
    Classroom classroom = classroomMapper.fromDto(classroomDto);
    classroom.setSchool(currentSchool());
    return classroomRepository.save(classroom);
  }

  public Page<Classroom> getClassrooms(PaginationParams params) {
    return classroomRepository.findAll(params.toPageable());
  }
}
