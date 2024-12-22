package com.isyll.agrotrade.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isyll.agrotrade.dto.mapper.UserMapper;
import com.isyll.agrotrade.dto.payload.request.SignUpRequest;
import com.isyll.agrotrade.dto.payload.request.SigninRequest;
import com.isyll.agrotrade.dto.payload.response.ApiResponse;
import com.isyll.agrotrade.dto.payload.response.JwtResponse;
import com.isyll.agrotrade.i18n.I18nUtil;
import com.isyll.agrotrade.models.User;
import com.isyll.agrotrade.services.UserService;

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
        String accessToken = userService.generateAccessToken(signinRequest.getUsername(), signinRequest.getPassword());
        String refreshToken = userService.generateRefreshToken(signinRequest.getUsername(),
                signinRequest.getPassword());
        JwtResponse response = new JwtResponse(accessToken, refreshToken);

        return ApiResponse.success(response).toReponseEntity();
    }

    @PostMapping("signup")
    public ResponseEntity<ApiResponse<Object>> signup(@RequestBody @Valid SignUpRequest signupRequest) {
        User user = new User();
        mapper.updateUserFromSignupRequest(signupRequest, user);
        userService.createUser(user);

        String message = i18nUtil.getMessage("auth.user_registered_successfully");

        return ApiResponse.success(message, HttpStatus.CREATED).toReponseEntity();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<JwtResponse>> refreshToken(
            @RequestBody Map<String, String> request) {
        String refreshToken = request.get("refresh_token");
        String acessToken = userService.generateFromRefreshToken(refreshToken);

        JwtResponse response = new JwtResponse(acessToken, refreshToken);

        return ApiResponse.success(response).toReponseEntity();
    }
}
