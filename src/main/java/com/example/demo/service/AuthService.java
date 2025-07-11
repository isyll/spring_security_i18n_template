package com.example.demo.service;

import com.example.demo.dto.auth.LoginRequest;
import com.example.demo.dto.auth.RefreshTokenRequest;
import com.example.demo.dto.response.TokenPair;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.UnauthorizedException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.JwtUtils;
import com.example.demo.utils.Translator;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService extends BaseService {

  private final UserRepository userRepository;
  private final Translator translator;
  private final JwtUtils jwtUtils;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsServiceImpl userDetailsService;
  private final JwtBlacklistService jwtBlacklistService;

  public TokenPair authenticate(LoginRequest request) {
    Authentication authentication = authenticateUser(request.identifier(), request.password());
    updateLastLoginTimestamp();

    return generateTokenPair(authentication);
  }

  public TokenPair authenticateFromRefreshToken(RefreshTokenRequest request) {
    String refreshToken = request.refreshToken();

    if (!jwtUtils.validateRefreshToken(refreshToken)) {
      throw new BadRequestException(translator.t("error.invalid_token"));
    }

    String username = jwtUtils.getUsernameFromJwtToken(refreshToken);
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    Authentication authentication =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    SecurityContextHolder.getContext().setAuthentication(authentication);
    updateLastLoginTimestamp();

    return new TokenPair(
        jwtUtils.generateAccessToken(authentication),
        refreshToken,
        currentUser().mustChangePassword());
  }

  public void blacklistToken(String token) {
    if (!jwtUtils.validateJwtToken(token)) {
      throw new UnauthorizedException(translator.t("message.logout.invalid"));
    }
    long remainingMs = jwtUtils.getExpirationDuration(token);
    jwtBlacklistService.blacklistToken(token, remainingMs);
  }

  private Authentication authenticateUser(String username, String password) {
    var authToken = new UsernamePasswordAuthenticationToken(username, password);
    var authentication = authenticationManager.authenticate(authToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return authentication;
  }

  private TokenPair generateTokenPair(Authentication authentication) {
    return new TokenPair(
        jwtUtils.generateAccessToken(authentication),
        jwtUtils.generateRefreshToken(authentication),
        currentUser().mustChangePassword());
  }

  private void updateLastLoginTimestamp() {
    User user = currentUser();
    user.setLastLoginAt(LocalDateTime.now());
    userRepository.save(user);
  }
}
