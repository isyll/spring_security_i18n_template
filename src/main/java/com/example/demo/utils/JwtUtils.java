package com.example.demo.utils;

import com.example.demo.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {

  private final TokenKeyManager keyManager;

  @Value("${JWT_EXPIRATION}")
  private Long jwtExpirationMs;

  @Value("${JWT_REFRESH_EXPIRATION}")
  private Long refreshExpirationMs;

  public String generateAccessToken(Authentication authentication) {
    User user = (User) authentication.getPrincipal();

    return Jwts.builder()
        .claim("type", "access")
        .claim("id", user.getPublicId())
        .claim("name", user.getFullName())
        .subject(user.getUsername())
        .issuedAt(new Date())
        .expiration(calculateExpiryDate(jwtExpirationMs))
        .signWith(keyManager.getKey(), Jwts.SIG.HS256)
        .compact();
  }

  public String generateRefreshToken(Authentication authentication) {
    User user = (User) authentication.getPrincipal();

    return Jwts.builder()
        .claim("type", "refresh")
        .claim("id", user.getPublicId())
        .subject(user.getUsername())
        .issuedAt(new Date())
        .expiration(calculateExpiryDate(refreshExpirationMs))
        .signWith(keyManager.getKey(), Jwts.SIG.HS256)
        .compact();
  }

  public String getUsernameFromJwtToken(String token) {
    return keyManager.payload(token).getSubject();
  }

  public boolean validateJwtToken(String token) {
    try {
      keyManager.parser().parse(token);
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

  public boolean validateRefreshToken(String token) {
    try {
      Claims claims = keyManager.payload(token);
      return "refresh".equalsIgnoreCase(claims.get("type", String.class));
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  public boolean checkTokenType(String token, String type) {
    Claims claims = keyManager.payload(token);
    return type.equals(claims.get("type", String.class));
  }

  public long getExpirationDuration(String token) {
    Claims claims = keyManager.payload(token);

    Date expiration = claims.getExpiration();
    long now = System.currentTimeMillis();

    return expiration.getTime() - now;
  }

  private Date calculateExpiryDate(long duration) {
    return new Date((new Date()).getTime() + duration);
  }
}
