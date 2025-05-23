package com.example.demo.utils;

import com.example.demo.service.UserDetailsImpl;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
  private SecurityUtils() {}

  public static Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  public static Optional<UserDetailsImpl> getUserDetails() {
    Authentication auth = getAuthentication();
    if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
      return Optional.empty();
    }
    return Optional.of((UserDetailsImpl) auth.getPrincipal());
  }

  public static Optional<UUID> getCurrentUserId() {
    return getUserDetails().map(UserDetailsImpl::getId);
  }

  public static boolean hasRole(String role) {
    Collection<? extends GrantedAuthority> authorities = getAuthentication().getAuthorities();
    return authorities.stream().anyMatch(auth -> auth.getAuthority().equals(role));
  }

  public static boolean isAuthenticated() {
    Authentication auth = getAuthentication();
    return auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal());
  }
}
