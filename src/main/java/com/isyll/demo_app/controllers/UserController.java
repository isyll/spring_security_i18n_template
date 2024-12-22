package com.isyll.agrotrade.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isyll.agrotrade.dto.mapper.UserMapper;
import com.isyll.agrotrade.dto.payload.request.UpdateUserRequest;
import com.isyll.agrotrade.dto.payload.response.ApiResponse;
import com.isyll.agrotrade.i18n.I18nUtil;
import com.isyll.agrotrade.models.User;
import com.isyll.agrotrade.services.UserDetailsImpl;
import com.isyll.agrotrade.services.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/user/account")
@Tag(name = "User API", description = "API to manage user accounts.")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper mapper;

    @Autowired
    I18nUtil i18nUtil;

    @PutMapping("/update-account")
    public ResponseEntity<ApiResponse<User>> updateMyAccount(@RequestBody final UpdateUserRequest dataRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userService.findUserById(userDetails.getId());

        mapper.updateUserFromUpdateRequest(dataRequest, user);
        User updatedUser = userService.updateUser(user, dataRequest);

        return ApiResponse.success(updatedUser).toReponseEntity();
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<ApiResponse<Object>> deleteMyAccount() {
        userService.deleteMyAccount();

        String message = i18nUtil.getMessage(
                "account.account_closed_successfully.account_closed_successfully");

        return ApiResponse.success(message).toReponseEntity();
    }

    @GetMapping("/my-informations")
    public ResponseEntity<ApiResponse<User>> myInformations() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = userService.findUserById(userDetails.getId());

        return ApiResponse.success(user).toReponseEntity();
    }
}
