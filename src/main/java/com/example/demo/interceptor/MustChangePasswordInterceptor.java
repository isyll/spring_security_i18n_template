package com.example.demo.interceptor;

import com.example.demo.model.User;
import com.example.demo.utils.ResponseWriter;
import com.example.demo.utils.SecurityUtils;
import com.example.demo.utils.Translator;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class MustChangePasswordInterceptor implements HandlerInterceptor {

  private static final List<String> allowedUrls =
      List.of("/account/profile", "/account/change-password", "/auth/logout");
  private final Translator translator;

  @Override
  public boolean preHandle(
      @Nonnull HttpServletRequest request,
      @Nonnull HttpServletResponse response,
      @Nonnull Object handler)
      throws Exception {

    if (SecurityUtils.isUnauthenticated()) {
      return true;
    }

    String path = request.getRequestURI();
    boolean isAllowed = allowedUrls.stream().anyMatch(path::endsWith);
    User user = SecurityUtils.getCurrentUser();

    if (user.mustChangePassword() && !isAllowed) {
      ResponseWriter.write(
          request,
          response,
          translator.t("error.must_change_password"),
          HttpServletResponse.SC_FORBIDDEN);
      return false;
    }

    return true;
  }
}
