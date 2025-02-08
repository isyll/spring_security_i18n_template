package com.example.demo.features.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.core.payload.ApiResponse;
import com.example.demo.features.auth.dto.JwtResponse;
import com.example.demo.features.auth.dto.RefreshTokenRequest;
import com.example.demo.features.auth.dto.SigninRequest;
import com.example.demo.features.auth.services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RequestMapping("/auth")
@RestController
@Tag(name = "Authentication", description = "API to manage authentication.")
public class AuthController {

    @Autowired
    private AuthService userService;

    @PostMapping("/signin")
    @Operation(summary = "Sign in", description = "Log in with his or her email and password.")
    public ResponseEntity<ApiResponse<JwtResponse>> signin(@RequestBody @Valid SigninRequest signinRequest) {
        String email = signinRequest.getEmail();
        String password = signinRequest.getPassword();
        String accessToken = userService.generateAccessToken(email, password);
        String refreshToken = userService.generateRefreshToken(email, password);
        JwtResponse response = new JwtResponse(accessToken, refreshToken);
        return ApiResponse.success(response).toReponseEntity();
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh token", description = "Get new access token with his or her refresh token.")
    public ResponseEntity<ApiResponse<JwtResponse>> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        String accessToken = userService.generateFromRefreshToken(refreshToken);
        JwtResponse response = new JwtResponse(accessToken, refreshToken);
        return ApiResponse.success(response).toReponseEntity();
    }
}
