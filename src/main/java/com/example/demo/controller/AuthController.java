package com.example.demo.controller;

import com.example.demo.dto.mapper.UserMapper;
import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RefreshTokenRequest;
import com.example.demo.dto.request.SignupRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.JwtResponse;
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
public class AuthController extends BaseController {

  @Autowired private AuthService authService;
  @Autowired private UserService userService;
  @Autowired private UserMapper userMapper;

  @PostMapping("/login")
  @Operation(summary = "Login", description = "Log in with email and password.")
  public ResponseEntity<ApiResponse<JwtResponse>> login(@RequestBody @Valid LoginRequest request) {
    JwtResponse response = authService.authenticate(request);
    return ok(response);
  }

  @PostMapping("/signup")
  @Operation(summary = "Sign up", description = "Create new account.")
  public ResponseEntity<ApiResponse<User>> signup(@RequestBody @Valid SignupRequest request) {
    User createdUser = userService.registerUser(userMapper.toUser(request));
    return ok(createdUser);
  }

  @PostMapping("/refresh-token")
  @Operation(summary = "Refresh token", description = "Get new access token from refresh token.")
  public ResponseEntity<ApiResponse<JwtResponse>> refreshToken(
      @RequestBody @Valid RefreshTokenRequest request) {
    String accessToken = authService.authenticateFromRefreshToken(request);
    JwtResponse response = new JwtResponse(accessToken, request.getRefreshToken());
    return ok(response);
  }
}
