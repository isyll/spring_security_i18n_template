package com.example.demo.service;

import com.example.demo.dto.mapper.EducationLevelMapper;
import com.example.demo.dto.school.EducationLevelDto;
import com.example.demo.model.EducationLevel;
import com.example.demo.repository.EducationLevelRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EducationLevelService extends BaseService {

  private final EducationLevelRepository educationLevelRepository;
  private final EducationLevelMapper educationLevelMapper;

  @Transactional
  public EducationLevel createEducationLevel(EducationLevelDto educationLevelDto) {
    EducationLevel educationLevel = educationLevelMapper.fromDto(educationLevelDto);
    educationLevel.setSchool(currentSchool());
    return educationLevelRepository.save(educationLevel);
  }

  public List<EducationLevel> getEducationLevels() {
    return educationLevelRepository.findBySchool(currentSchool());
  }
}
