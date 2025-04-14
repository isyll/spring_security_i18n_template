package com.example.demo.core.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@ConfigurationProperties
@PropertySource("classpath:custom.properties")
public class CustomProperties {

  @Value("${pagination.limit}")
  private int paginationLimit;

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.refreshExpirationMs}")
  private long refreshExpirationMs;

  @Value("${jwt.jwtExpirationMs}")
  private long jwtExpirationMs;
}
