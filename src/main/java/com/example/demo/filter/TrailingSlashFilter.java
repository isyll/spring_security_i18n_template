package com.example.demo.filter;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TrailingSlashFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      @Nonnull HttpServletRequest request,
      @Nonnull HttpServletResponse response,
      @Nonnull FilterChain filterChain)
      throws ServletException, IOException {
    String requestUri = request.getRequestURI();
    String contextPath = request.getContextPath() + "/";

    if (!requestUri.equals(contextPath) && requestUri.endsWith("/")) {
      String newUrl = requestUri.substring(0, requestUri.length() - 1);
      response.setStatus(HttpStatus.MOVED_PERMANENTLY.value());
      response.setHeader(HttpHeaders.LOCATION, newUrl);
      return;
    }

    filterChain.doFilter(request, response);
  }
}
