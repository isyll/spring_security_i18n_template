package com.example.demo.service;

import com.example.demo.config.i18n.I18nUtil;
import com.example.demo.config.security.jwt.JwtUtils;
import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RefreshTokenRequest;
import com.example.demo.dto.response.JwtResponse;
import com.example.demo.exceptions.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final I18nUtil i18nUtil;
  private final JwtUtils jwtUtils;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsServiceImpl userDetailsService;

  public AuthService(
      JwtUtils jwtUtils,
      I18nUtil i18nUtil,
      AuthenticationManager authenticationManager,
      UserDetailsServiceImpl userDetailsService) {
    this.jwtUtils = jwtUtils;
    this.i18nUtil = i18nUtil;
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
  }

  public JwtResponse authenticate(LoginRequest request) {
    Authentication authentication =
        generateAuthentication(request.getEmail(), request.getPassword());
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String accessToken = jwtUtils.generateAccessToken(authentication);
    String refreshToken = jwtUtils.generateRefreshToken(authentication);
    return new JwtResponse(accessToken, refreshToken);
  }

  public String authenticateFromRefreshToken(RefreshTokenRequest request) {
    String refreshToken = request.getRefreshToken();

    if (!jwtUtils.validateJwtToken(refreshToken)) {
      String message = i18nUtil.getMessage("error.invalid_token");
      throw new BadRequestException(message);
    }

    if (!jwtUtils.checkTokenType(refreshToken, "refresh")) {
      String message = i18nUtil.getMessage("error.bad_refresh_token");
      throw new BadRequestException(message);
    }

    String username = jwtUtils.getUsernameFromJwtToken(refreshToken);
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);

    return jwtUtils.generateAccessToken(authentication);
  }

  private Authentication generateAuthentication(String username, String password) {
    UsernamePasswordAuthenticationToken authToken =
        new UsernamePasswordAuthenticationToken(username, password);
    Authentication authentication = authenticationManager.authenticate(authToken);

    SecurityContextHolder.getContext().setAuthentication(authentication);
    return authentication;
  }
}
