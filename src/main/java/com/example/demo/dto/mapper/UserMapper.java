package com.example.demo.dto.mapper;

import com.example.demo.dto.request.SignupRequest;
import com.example.demo.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "roles", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "roleName", ignore = true)
  User toUser(SignupRequest signupRequest);
}
