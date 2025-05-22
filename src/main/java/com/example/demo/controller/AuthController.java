package com.example.demo.controller;

import com.example.demo.dto.mapper.UserMapper;
import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RefreshTokenRequest;
import com.example.demo.dto.request.SignupRequest;
import com.example.demo.dto.response.common.ApiResponse;
import com.example.demo.dto.response.JwtResponse;
import com.example.demo.model.User;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@Tag(name = "Authentication", description = "API to manage authentication.")
public class AuthController extends BaseController {

  private final AuthService authService;
  private final UserService userService;
  private final UserMapper userMapper;

  public AuthController(AuthService authService, UserService userService, UserMapper userMapper) {
    this.authService = authService;
    this.userService = userService;
    this.userMapper = userMapper;
  }

  @PostMapping("/login")
  @Operation(summary = "Login", description = "Log in with email and password.")
  public ResponseEntity<ApiResponse<JwtResponse>> login(@RequestBody @Valid LoginRequest request) {
    return ok(authService.authenticate(request));
  }

  @PostMapping("/signup")
  @Operation(summary = "Sign up", description = "Create new account.")
  public ResponseEntity<ApiResponse<User>> signup(@RequestBody @Valid SignupRequest request) {
    User createdUser = userService.registerUser(userMapper.toUser(request));
    HttpStatus status = HttpStatus.CREATED;
    return ok(createdUser, status);
  }

  @PostMapping("/refresh-token")
  @Operation(summary = "Refresh token", description = "Get new access token from refresh token.")
  public ResponseEntity<ApiResponse<JwtResponse>> refreshToken(
      @RequestBody @Valid RefreshTokenRequest request) {
    String accessToken = authService.authenticateFromRefreshToken(request);
    return ok(new JwtResponse(accessToken, request.getRefreshToken()));
  }
}
