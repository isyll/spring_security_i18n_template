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
    load("Roles", this::loadRoles);
    load("Permissions", this::loadPermissions);
  }

  private void load(String name, Runnable task) {
    try {
      task.run();
      log.info("{} initialized successfully", name);
    } catch (Exception e) {
      log.error("Error initializing {}", name, e);
    }
  }

  private void loadRoles() {}

  private void loadPermissions() {}
}
