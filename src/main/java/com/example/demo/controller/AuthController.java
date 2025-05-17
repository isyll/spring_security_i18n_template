package com.example.demo.controller;

import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.JwtResponse;
import com.example.demo.dto.request.RefreshTokenRequest;
import com.example.demo.dto.request.SigninRequest;
import com.example.demo.dto.request.SignupRequest;
import com.example.demo.dto.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@Tag(name = "Authentication", description = "API to manage authentication.")
public class AuthController {

  @Autowired private AuthService authService;

  @Autowired private UserService userService;

  @Autowired private UserMapper userMapper;

  @PostMapping("/signin")
  @Operation(summary = "Sign in", description = "Log in with his or her email and password.")
  public ResponseEntity<ApiResponse<JwtResponse>> signin(
      @RequestBody @Valid SigninRequest signinRequest) {
    String email = signinRequest.getEmail();
    String password = signinRequest.getPassword();

    String accessToken = authService.generateAccessToken(email, password);
    String refreshToken = authService.generateRefreshToken(email, password);
    JwtResponse response = new JwtResponse(accessToken, refreshToken);
    return ApiResponse.success(response).toResponseEntity();
  }

  @PostMapping("/signup")
  @Operation(summary = "Sign up", description = "Register a new user.")
  public ResponseEntity<ApiResponse<User>> signup(@RequestBody @Valid SignupRequest signupRequest) {
    User registeredUser = userService.registerUser(userMapper.toUser(signupRequest));
    return ApiResponse.success(registeredUser).toResponseEntity();
  }

  @PostMapping("/refresh-token")
  @Operation(summary = "Refresh token", description = "Get new access token from refresh token.")
  public ResponseEntity<ApiResponse<JwtResponse>> refreshToken(
      @RequestBody @Valid RefreshTokenRequest request) {
    String refreshToken = request.getRefreshToken();
    String accessToken = authService.generateFromRefreshToken(refreshToken);
    JwtResponse response = new JwtResponse(accessToken, refreshToken);
    return ApiResponse.success(response).toResponseEntity();
  }
}
