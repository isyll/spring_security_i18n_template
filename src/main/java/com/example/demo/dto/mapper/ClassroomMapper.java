package com.example.demo.dto.mapper;

import com.example.demo.dto.school.ClassroomDto;
import com.example.demo.model.Classroom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "school", ignore = true)
  Classroom fromDto(ClassroomDto classroomDto);
}
