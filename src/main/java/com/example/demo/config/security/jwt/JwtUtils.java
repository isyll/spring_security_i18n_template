package com.example.demo.config.security.jwt;

import com.example.demo.service.UserDetailsImpl;
import com.example.demo.utils.CustomProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtils {

  @Autowired CustomProperties customProperties;

  public String generateAccessToken(Authentication authentication) {
    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

    return Jwts.builder()
        .claim("type", "access")
        .claim("id", userPrincipal.getId())
        .claim("email", userPrincipal.getEmail())
        .claim("first_name", userPrincipal.getFirstName())
        .claim("last_name", userPrincipal.getLastName())
        .claim("phone", userPrincipal.getPhone())
        .subject(userPrincipal.getUsername())
        .issuedAt(new Date())
        .expiration(new Date((new Date()).getTime() + customProperties.getJwtExpirationMs()))
        .signWith(key(), Jwts.SIG.HS256)
        .compact();
  }

  public String generateRefreshToken(Authentication authentication) {
    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

    return Jwts.builder()
        .claim("type", "refresh")
        .subject(userPrincipal.getUsername())
        .issuedAt(new Date())
        .expiration(new Date((new Date()).getTime() + customProperties.getRefreshExpirationMs()))
        .signWith(key(), Jwts.SIG.HS256)
        .compact();
  }

  public String getUserNameFromJwtToken(String token) {
    return payload(token).getSubject();
  }

  public Long getIdFromJwtToken(String token) {
    return payload(token).get("id", Long.class);
  }

  public boolean validateJwtToken(String token) {
    try {
      parser().parse(token);
      return true;
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }

  public boolean checkTokenType(String token, String type) {
    Claims claims = payload(token);
    return type.equals(claims.get("type", String.class));
  }

  public UserDetailsImpl getUserDetails() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return (UserDetailsImpl) auth.getPrincipal();
  }

  private Claims payload(String token) {
    return parser().parseSignedClaims(token).getPayload();
  }

  private SecretKey key() {
    String jwtSecret = customProperties.getJwtSecret();
    return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
  }

  private JwtParser parser() {
    return Jwts.parser().verifyWith(key()).build();
  }
}
