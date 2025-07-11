package com.example.demo.utils;

import com.example.demo.model.User;
import java.util.Optional;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class SecurityUtils {

  public Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  public Optional<User> getUserDetails() {
    if (isUnauthenticated()) {
      return Optional.empty();
    }
    Authentication auth = getAuthentication();
    return Optional.of((User) auth.getPrincipal());
  }

  public User getCurrentUser() {
    return getUserDetails().orElse(null);
  }

  public boolean isUnauthenticated() {
    Authentication auth = getAuthentication();
    return auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getName());
  }
}
