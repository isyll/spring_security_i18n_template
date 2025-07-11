package com.example.demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenKeyManager {

  @Value("${JWT_SECRET}")
  private String jwtSecret;

  private SecretKey key;

  private JwtParser parser;

  public SecretKey getKey() {
    if (key == null) {
      key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
    return key;
  }

  public Claims payload(String token) {
    return parser().parseSignedClaims(token).getPayload();
  }

  public JwtParser parser() {
    if (parser == null) {
      parser = Jwts.parser().verifyWith(getKey()).build();
    }
    return parser;
  }
}
