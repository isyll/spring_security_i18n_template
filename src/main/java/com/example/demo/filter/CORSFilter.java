package com.example.demo.filter;

import com.example.demo.config.constants.AppInfo;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class CORSFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      @Nonnull HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    response.setHeader("Server", AppInfo.APP_NAME);
    response.setHeader("X-App-Name", AppInfo.APP_NAME);
    response.setHeader("X-App-Version", AppInfo.APP_VERSION);
    response.setHeader("X-App-Developer", AppInfo.AUTHOR);

    response.addHeader("Access-Control-Allow-Origin", "*");
    response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
    response.addHeader(
        "Access-Control-Allow-Headers",
        "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
    response.addHeader(
        "Access-Control-Expose-Headers",
        "Access-Control-Allow-Origin, Access-Control-Allow-Credentials");
    response.addHeader("Access-Control-Allow-Credentials", "true");
    response.addIntHeader("Access-Control-Max-Age", 10);
    filterChain.doFilter(request, response);
  }
}
