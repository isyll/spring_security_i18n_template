package com.example.demo.features.auth.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.features.auth.dto.SignupRequest;
import com.example.demo.features.users.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roleName", ignore = true)
    public User toUser(SignupRequest signupRequest);

}
