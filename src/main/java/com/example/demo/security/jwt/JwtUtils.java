package com.example.demo.security.jwt;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.demo.core.utils.CustomProperties;
import com.example.demo.features.auth.services.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtils {

    @Autowired
    CustomProperties customProperties;

    public String generateAccessToken(Authentication authentication) {
        List<String> roles = new ArrayList<>();
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        userPrincipal.getAuthorities().forEach((authority) -> {
            roles.add(authority.getAuthority());
        });

        return Jwts.builder()
                .claim("type", "access")
                .claim("id", userPrincipal.getId())
                .claim("email", userPrincipal.getEmail())
                .claim("first_name", userPrincipal.getFirstName())
                .claim("last_name", userPrincipal.getLastName())
                .claim("phone", userPrincipal.getPhone())
                .claim("permissions", roles)
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
        try {
            return Keys.hmacShaKeyFor(jwtSecret.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new InternalAuthenticationServiceException(jwtSecret);
        }
    }

    private JwtParser parser() {
        return Jwts.parser().verifyWith(key()).build();
    }
}
