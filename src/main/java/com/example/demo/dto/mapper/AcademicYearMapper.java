package com.example.demo.dto.mapper;

import com.example.demo.dto.school.AcademicYearDto;
import com.example.demo.model.AcademicYear;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AcademicYearMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "name", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "school", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  AcademicYear fromDto(AcademicYearDto yearDto);
}
