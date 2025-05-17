package com.example.demo.service;

import com.example.demo.core.exceptions.BadRequestException;
import com.example.demo.i18n.I18nUtil;
import com.example.demo.model.AccountStatus;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  @Autowired private UserRepository userRepository;

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private I18nUtil i18nUtil;

  @Autowired private JwtUtils jwtUtils;

  @Autowired private UserDetailsServiceImpl userDetailsService;

  public void deleteMyAccount() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    User user = userRepository.findById(userDetails.getId()).orElseThrow(RuntimeException::new);

    user.setStatus(AccountStatus.DELETED);
    userRepository.save(user);
  }

  public String generateAccessToken(String username, String password) {
    Authentication authentication = generateAuthentication(username, password);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    return jwtUtils.generateAccessToken(authentication);
  }

  public String generateRefreshToken(String username, String password) {
    Authentication authentication = generateAuthentication(username, password);
    return jwtUtils.generateRefreshToken(authentication);
  }

  public String generateFromRefreshToken(String refreshToken) {
    if (refreshToken == null || refreshToken.isEmpty()) {
      String message = i18nUtil.getMessage("error.refresh_token_missing");
      throw new BadRequestException(message);
    }

    if (!jwtUtils.validateJwtToken(refreshToken)) {
      String message = i18nUtil.getMessage("error.invalid_token");
      throw new BadRequestException(message);
    }

    if (!jwtUtils.checkTokenType(refreshToken, "refresh")) {
      String message = i18nUtil.getMessage("error.bad_refresh_token");
      throw new BadRequestException(message);
    }

    String username = jwtUtils.getUserNameFromJwtToken(refreshToken);
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
