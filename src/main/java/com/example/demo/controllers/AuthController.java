package com.example.demo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.i18n.I18nUtil;
import com.example.demo.dto.mapper.UserMapper;
import com.example.demo.dto.payload.request.SignUpRequest;
import com.example.demo.dto.payload.request.SigninRequest;
import com.example.demo.dto.payload.response.ApiResponse;
import com.example.demo.dto.payload.response.JwtResponse;
import com.example.demo.models.User;
import com.example.demo.services.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication API", description = "API to manage authentication.")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper mapper;

    @Autowired
    I18nUtil i18nUtil;

    @PostMapping("signin")
    public ResponseEntity<ApiResponse<JwtResponse>> signin(@RequestBody @Valid SigninRequest signinRequest) {
        String username = signinRequest.getUsername();
        String password = signinRequest.getPassword();

        String accessToken = userService.generateAccessToken(username, password);
        String refreshToken = userService.generateRefreshToken(username, password);
        JwtResponse response = new JwtResponse(accessToken, refreshToken);

        return ApiResponse.success(response).toReponseEntity();
    }

    @PostMapping("signup")
    public ResponseEntity<ApiResponse<User>> signup(@RequestBody @Valid SignUpRequest signupRequest) {
        User user = new User();
        mapper.updateUserFromSignupRequest(signupRequest, user);
        User createdUser = userService.createUser(user);

        String message = i18nUtil.getMessage("auth.user_registered_successfully");

        return ApiResponse.success(createdUser, message, HttpStatus.CREATED).toReponseEntity();
    }

    @PostMapping("refresh-token")
    public ResponseEntity<ApiResponse<JwtResponse>> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refresh_token");
        String acessToken = userService.generateFromRefreshToken(refreshToken);

        JwtResponse response = new JwtResponse(acessToken, refreshToken);

        return ApiResponse.success(response).toReponseEntity();
    }
}
