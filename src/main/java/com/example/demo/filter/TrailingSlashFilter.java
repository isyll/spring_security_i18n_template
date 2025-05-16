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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TrailingSlashFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      @Nonnull HttpServletResponse response,
      @Nonnull FilterChain filterChain)
      throws ServletException, IOException {
    if (request.getRequestURI().endsWith("/")) {
      ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromRequest(request);
      String path = builder.build().getPath();
      assert path != null;
      builder.replacePath(String.format("%s", path.substring(0, path.length() - 1)));
      response.setStatus(HttpStatus.PERMANENT_REDIRECT.value());
      response.setHeader(HttpHeaders.LOCATION, builder.toUriString());
    } else {
      filterChain.doFilter(request, response);
    }
  }
}
