package com.isyll.demo_app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isyll.demo_app.dto.mapper.UserMapper;
import com.isyll.demo_app.dto.payload.request.SignUpRequest;
import com.isyll.demo_app.dto.payload.request.SigninRequest;
import com.isyll.demo_app.dto.payload.response.ApiResponse;
import com.isyll.demo_app.dto.payload.response.JwtResponse;
import com.isyll.demo_app.i18n.I18nUtil;
import com.isyll.demo_app.models.User;
import com.isyll.demo_app.security.jwt.JwtUtils;
import com.isyll.demo_app.services.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication API", description = "API to manage authentication.")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserMapper mapper;

    @Autowired
    I18nUtil i18nUtil;

    @PostMapping("signin")
    public ResponseEntity<ApiResponse<JwtResponse>> signin(@RequestBody @Valid SigninRequest signinRequest) {
        UsernamePasswordAuthenticationToken authToekn = new UsernamePasswordAuthenticationToken(
                signinRequest.getUsername(), signinRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToekn);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);
        return ApiResponse.success(new JwtResponse(jwt)).toReponseEntity();
    }

    @PostMapping("signup")
    public ResponseEntity<ApiResponse<Object>> signup(@RequestBody @Valid SignUpRequest signupRequest) {
        User user = new User();
        mapper.updateUserFromSignupRequest(signupRequest, user);
        userService.createUser(user);

        String message = i18nUtil.getMessage("auth.user_registered_successfully");

        return ApiResponse.success(message, HttpStatus.CREATED).toReponseEntity();
    }
}
