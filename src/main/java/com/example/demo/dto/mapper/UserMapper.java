package com.example.demo.dto.mapper;

import com.example.demo.dto.user.admin.CreateAdminRequest;
import com.example.demo.dto.user.staff.CreateStaffRequest;
import com.example.demo.dto.user.student.CreateStudentRequest;
import com.example.demo.dto.user.student.UpdateStudentRequest;
import com.example.demo.dto.user.teacher.CreateTeacherRequest;
import com.example.demo.dto.user.teacher.UpdateTeacherRequest;
import com.example.demo.model.Admin;
import com.example.demo.model.Staff;
import com.example.demo.model.Student;
import com.example.demo.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

  @Mapping(target = "registrationNumber", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "school", ignore = true)
  @Mapping(target = "roles", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "deletedAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "lastLoginAt", ignore = true)
  @Mapping(target = "mustChangePassword", ignore = true)
  @Mapping(target = "emailVerified", ignore = true)
  @Mapping(target = "subjects", ignore = true)
  Teacher fromRequest(CreateTeacherRequest request);

  @Mapping(target = "registrationNumber", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "school", ignore = true)
  @Mapping(target = "roles", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "deletedAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "lastLoginAt", ignore = true)
  @Mapping(target = "mustChangePassword", ignore = true)
  @Mapping(target = "emailVerified", ignore = true)
  Student fromRequest(CreateStudentRequest request);

  @Mapping(target = "registrationNumber", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "school", ignore = true)
  @Mapping(target = "roles", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "deletedAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "mustChangePassword", ignore = true)
  @Mapping(target = "lastLoginAt", ignore = true)
  @Mapping(target = "emailVerified", ignore = true)
  Admin fromRequest(CreateAdminRequest request);

  @Mapping(target = "registrationNumber", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "school", ignore = true)
  @Mapping(target = "roles", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "deletedAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "mustChangePassword", ignore = true)
  @Mapping(target = "lastLoginAt", ignore = true)
  @Mapping(target = "emailVerified", ignore = true)
  Staff fromRequest(CreateStaffRequest request);

  @Mapping(target = "registrationNumber", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "password", ignore = true)
  @Mapping(target = "school", ignore = true)
  @Mapping(target = "roles", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "deletedAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "lastLoginAt", ignore = true)
  @Mapping(target = "mustChangePassword", ignore = true)
  @Mapping(target = "emailVerified", ignore = true)
  void updateFromRequest(UpdateStudentRequest request, @MappingTarget Student student);

  @Mapping(target = "registrationNumber", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "password", ignore = true)
  @Mapping(target = "school", ignore = true)
  @Mapping(target = "roles", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "deletedAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "lastLoginAt", ignore = true)
  @Mapping(target = "mustChangePassword", ignore = true)
  @Mapping(target = "emailVerified", ignore = true)
  void updateFromRequest(UpdateTeacherRequest request, @MappingTarget Teacher teacher);
}
