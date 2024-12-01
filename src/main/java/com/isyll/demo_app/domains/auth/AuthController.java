package com.isyll.demo_app.domains.auth;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.isyll.demo_app.common.payload.JwtResponse;
import com.isyll.demo_app.common.payload.MessageResponse;
import com.isyll.demo_app.common.payload.auth.LoginWithUsernameRequest;
import com.isyll.demo_app.common.utils.JwtUtils;
import com.isyll.demo_app.domains.user.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> signin(@RequestBody @Valid LoginWithUsernameRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("signup")
    public ResponseEntity<MessageResponse> signup(@RequestBody @Valid SignUpRequest signUpRequest) {
        userService.saveUser(new User(null, null, signUpRequest.getUsername(), signUpRequest.getPassword(),
                signUpRequest.getPasswordConfirm(), signUpRequest.getEmail(), signUpRequest.getPhone(),
                signUpRequest.getGender(), signUpRequest.getFirstname(), signUpRequest.getLastname(),
                null, null, false)
        );
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
