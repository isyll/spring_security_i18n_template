package com.example.demo.filter;

import com.example.demo.service.JwtBlacklistService;
import com.example.demo.service.UserDetailsServiceImpl;
import com.example.demo.utils.JwtUtils;
import com.example.demo.utils.ResponseWriter;
import com.example.demo.utils.Translator;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

  private final JwtUtils jwtUtils;
  private final UserDetailsServiceImpl userDetailsService;
  private final JwtBlacklistService jwtBlacklistService;
  private final Translator translator;

  @Override
  protected void doFilterInternal(
      @Nonnull HttpServletRequest request,
      @Nonnull HttpServletResponse response,
      @Nonnull FilterChain filterChain)
      throws ServletException, IOException {
    String token = extractToken(request);

    if (isValidAccessToken(token)) {
      if (jwtBlacklistService.isTokenBlacklisted(token)) {
        ResponseWriter.write(
            request,
            response,
            translator.t("error.session_invalid"),
            HttpServletResponse.SC_UNAUTHORIZED);
        return;
      }

      authenticate(token, request);
    }

    filterChain.doFilter(request, response);
  }

  private String extractToken(HttpServletRequest request) {
    String header = request.getHeader("Authorization");
    return (StringUtils.hasText(header) && header.startsWith("Bearer "))
        ? header.substring(7)
        : null;
  }

  private boolean isValidAccessToken(String token) {
    return token != null
        && jwtUtils.validateJwtToken(token)
        && jwtUtils.checkTokenType(token, "access");
  }

  private void authenticate(String token, HttpServletRequest request) {
    try {
      String username = jwtUtils.getUsernameFromJwtToken(token);
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);

      var auth =
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

      SecurityContextHolder.getContext().setAuthentication(auth);
    } catch (Exception e) {
      log.error("Authentication failed: {}", e.getMessage());
    }
  }
}
