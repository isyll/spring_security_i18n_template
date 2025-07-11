package com.example.demo.interceptor;

import com.example.demo.common.annotation.RequireSchoolContext;
import com.example.demo.context.SchoolContextHolder;
import com.example.demo.model.School;
import com.example.demo.model.User;
import com.example.demo.utils.ResponseWriter;
import com.example.demo.utils.SecurityUtils;
import com.example.demo.utils.Translator;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class SchoolInterceptor implements HandlerInterceptor {

  private final Translator translator;
  private final SchoolContextHolder schoolContextHolder;

  @Override
  public boolean preHandle(
      @Nonnull HttpServletRequest request,
      @Nonnull HttpServletResponse response,
      @Nonnull Object handler)
      throws Exception {
    User currentUser = SecurityUtils.getCurrentUser();
    School currentSchool = schoolContextHolder.getSchool();

    if (currentUser == null) {
      return true;
    }

    if (handler instanceof HandlerMethod method) {
      boolean schoolIsRequired =
          method.hasMethodAnnotation(RequireSchoolContext.class)
              || method.getBeanType().isAnnotationPresent(RequireSchoolContext.class);

      if (schoolIsRequired && currentSchool == null) {
        return writeError(response, request, "error.school_context_required");
      }
    }

    if (!currentUser.isSuperAdmin()) {
      School userSchool = currentUser.getSchool();
      if (userSchool == null) {
        if (!currentUser.isAdmin()) {
          // This should never happen: all users except Admin and SuperAdmin must be associated with
          // a school
          throw new IllegalStateException(
              "Invalid state: non-admin users must always be associated with a school.");
        }
      }

      if (currentSchool != null) {
        if (!currentSchool.equals(userSchool)) {
          return writeError(response, request, "error.unauthorized_school");
        }
      }
    }

    return true;
  }

  private boolean writeError(
      HttpServletResponse response, HttpServletRequest request, String messageKey)
      throws Exception {
    String message = translator.t(messageKey);
    ResponseWriter.write(request, response, message, HttpServletResponse.SC_BAD_REQUEST);
    return false;
  }
}
