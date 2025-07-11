package com.example.demo.service;

import com.example.demo.dto.response.StatsOverview;
import com.example.demo.model.AcademicYear;
import com.example.demo.model.School;
import com.example.demo.repository.SchoolClassRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.service.shared.AcademicYearHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsService extends BaseService {

  private final StudentRepository studentRepository;
  private final TeacherRepository teacherRepository;
  private final SchoolClassRepository schoolClassRepository;
  private final AcademicYearHelper yearHelper;

  public StatsOverview getOverview() {
    School school = currentSchool();
    long studentsCount = studentRepository.countBySchool(school);
    long teachersCount = teacherRepository.countBySchool(school);
    long classesCount = schoolClassRepository.countBySchool(school);

    String currentAcademicYear =
        yearHelper.getCurrentAcademicYear().map(AcademicYear::getName).orElse(null);
    return new StatsOverview(studentsCount, teachersCount, classesCount, currentAcademicYear);
  }
}
