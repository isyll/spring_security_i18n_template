package com.example.demo.filter;

import com.example.demo.constants.HeaderNames;
import com.example.demo.context.SchoolContextHolder;
import com.example.demo.repository.SchoolRepository;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class SchoolFilter extends OncePerRequestFilter {

  private final SchoolRepository schoolRepository;
  private final SchoolContextHolder schoolContextHolder;

  @Override
  protected void doFilterInternal(
      @Nonnull HttpServletRequest request,
      @Nonnull HttpServletResponse response,
      @Nonnull FilterChain filterChain)
      throws ServletException, IOException {

    String schoolNumberValue = request.getHeader(HeaderNames.SCHOOL_HEADER_NAME);

    if (schoolNumberValue != null) {
      schoolRepository.findById(schoolNumberValue).ifPresent(schoolContextHolder::setSchool);
    }

    filterChain.doFilter(request, response);
  }
}
