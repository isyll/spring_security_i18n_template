package com.example.demo.dto.mapper;

import com.example.demo.dto.school.EducationLevelDto;
import com.example.demo.model.EducationLevel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EducationLevelMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "grades", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "school", ignore = true)
  EducationLevel fromDto(EducationLevelDto educationLevelDto);
}
