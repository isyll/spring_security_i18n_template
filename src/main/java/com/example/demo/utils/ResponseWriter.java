package com.example.demo.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

@UtilityClass
public class ResponseWriter {

  public void write(
      HttpServletRequest request, HttpServletResponse response, String message, int status)
      throws IOException {
    String path = request.getRequestURI();
    String timestamp = Instant.now().toString();
    boolean success = HttpStatus.valueOf(status).is2xxSuccessful();

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.setStatus(status);

    String body =
        """
        {
          "message": "%s",
          "success": %b,
          "status": %d,
          "timestamp": "%s",
          "path": "%s"
        }
        """
            .formatted(message, success, status, timestamp, path);

    response.getWriter().write(body);
  }
}
