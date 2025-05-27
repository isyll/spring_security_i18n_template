package com.example.demo.config.startup;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthInitializer {
  @PostConstruct
  public void init() {
    log.info("Authentication information initialized successfully.");
  }
}
