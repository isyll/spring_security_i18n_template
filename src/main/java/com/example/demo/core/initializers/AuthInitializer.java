package com.example.demo.core.initializers;

import com.example.demo.models.ERole;
import com.example.demo.models.Role;
import com.example.demo.repos.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthInitializer {

  @Autowired private RoleRepository roleRepository;

  @PostConstruct
  public void init() {
    try {
      loadRoles();
      log.info("Roles initialized successfully");
    } catch (Exception e) {
      log.error("Error initializing roles", e);
    }
  }

  private void loadRoles() {
    for (ERole roleName : ERole.values()) {
      Role role = roleRepository.findByName(roleName.name());
      if (role == null) {
        role = new Role();
      }
      role.setName(roleName.name());
      role.setDescription(roleName.name());
      roleRepository.save(role);
    }
  }
}
