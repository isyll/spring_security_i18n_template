package com.example.demo.utils;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@UtilityClass
public class RequestUtils {

  public HttpServletRequest getRequest() {
    return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
        .getRequest();
  }

  public String getCurrentPath() {
    return getRequest().getRequestURI();
  }

  public String getMethod() {
    return getRequest().getMethod();
  }

  public boolean isOperationRequest() {
    return List.of("POST", "PUT", "DELETE").contains(getMethod());
  }
}
