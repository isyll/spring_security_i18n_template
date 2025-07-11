package com.example.demo.startup;

import com.example.demo.model.Permission;
import com.example.demo.model.enums.EPermission;
import com.example.demo.repository.PermissionRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthLoader implements ApplicationRunner {

  private final PermissionRepository permissionRepository;

  @Override
  public void run(ApplicationArguments args) {
    if (loadPermissions()) {
      log.info("Authentication information updated successfully.");
    }
  }

  private boolean loadPermissions() {
    Set<EPermission> existing = permissionRepository.findAllPermissionNames();
    Set<EPermission> toCreate =
        Arrays.stream(EPermission.values())
            .filter(p -> !existing.contains(p))
            .collect(Collectors.toSet());

    if (toCreate.isEmpty()) {
      return false;
    }

    List<Permission> newPermissions =
        toCreate.stream().map(Permission::new).collect(Collectors.toList());

    permissionRepository.saveAll(newPermissions);
    newPermissions.forEach(p -> log.info("Permission {} has been created.", p.getName().name()));

    return true;
  }
}
