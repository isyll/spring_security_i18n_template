package com.example.demo.config.security.jwt;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.demo.services.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.refreshExpirationMs}")
    private long refreshExpirationMs;

    @Value("${jwt.jwtExpirationMs}")
    private long jwtExpirationMs;

    public String generateAccessToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return Jwts.builder()
                .claim("type", "access")
                .claims()
                .add("id", userPrincipal.getId())
                .add("email", userPrincipal.getEmail())
                .add("username", userPrincipal.getUsername())
                .add("first_name", userPrincipal.getFirstName())
                .add("last_name", userPrincipal.getLastName())
                .add("country_code", userPrincipal.getCountryCode())
                .add("phone", userPrincipal.getPhone())
                .add("gender", userPrincipal.getGender())
                .add("created_at", userPrincipal.getCreatedAt().toString())
                .add("updated_at", userPrincipal.getUpdatedAt().toString())
                .add("status", userPrincipal.getStatus())
                .add("email_verified", userPrincipal.isEmailVerified())
                .add("roles", roles)
                .and()
                .subject(userPrincipal.getUsername())
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), Jwts.SIG.HS512)
                .compact();
    }

    public String generateRefreshToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .claim("type", "refresh")
                .subject(userPrincipal.getUsername())
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + refreshExpirationMs))
                .signWith(key(), Jwts.SIG.HS512)
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
            Jwts.parser().verifyWith(key()).build().parse(token);
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

    public boolean isAccessToken(String token) {
        Claims claims = payload(token);
        return "access".equals(claims.get("type", String.class));
    }

    public UserDetailsImpl getUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsImpl) auth.getPrincipal();
    }

    private SecretKey key() {
        try {
            return Keys.hmacShaKeyFor(jwtSecret.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new InternalAuthenticationServiceException(jwtSecret);
        }
    }

    private Claims payload(String token) {
        return Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload();
    }
}
