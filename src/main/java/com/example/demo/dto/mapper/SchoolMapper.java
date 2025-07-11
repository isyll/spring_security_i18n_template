package com.example.demo.dto.mapper;

import com.example.demo.dto.school.SchoolDto;
import com.example.demo.model.School;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SchoolMapper {

  @Mapping(target = "schoolNumber", ignore = true)
  @Mapping(target = "imageUrl", ignore = true)
  @Mapping(target = "emailVerified", ignore = true)
  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "settings", ignore = true)
  School fromDto(SchoolDto schoolDto);

  @Mapping(target = "schoolNumber", ignore = true)
  @Mapping(target = "code", ignore = true)
  @Mapping(target = "imageUrl", ignore = true)
  @Mapping(target = "emailVerified", ignore = true)
  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "settings", ignore = true)
  void updateSchoolFromDto(SchoolDto dto, @MappingTarget School school);
}
