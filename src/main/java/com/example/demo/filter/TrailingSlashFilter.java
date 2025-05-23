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
    String uri = request.getRequestURI();
    String context = request.getContextPath();
    String normalizedContext = context.endsWith("/") ? context : context + "/";

    if (!uri.equals(normalizedContext) && uri.endsWith("/")) {
      String newUrl = uri.substring(0, uri.length() - 1);
      String query = request.getQueryString();

      if (query != null && !query.isEmpty()) {
        newUrl += "?" + query;
      }

      response.setStatus(HttpStatus.MOVED_PERMANENTLY.value());
      response.setHeader(HttpHeaders.LOCATION, newUrl);
    } else {
      filterChain.doFilter(request, response);
    }
  }
}
