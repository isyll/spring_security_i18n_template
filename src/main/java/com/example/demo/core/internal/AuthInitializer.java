package com.example.demo.core.internal;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.core.utils.IterableUtils;
import com.example.demo.features.roles.models.EPermission;
import com.example.demo.features.roles.models.Permission;
import com.example.demo.features.roles.models.Role;
import com.example.demo.features.roles.repository.PermissionRepository;
import com.example.demo.features.roles.repository.RoleRepository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthInitializer {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @PostConstruct
    public void init() {
        log.info("Initializing roles and permissions...");
        try {
            loadPermissions();
            loadRoles();
        } catch (Exception e) {
            log.error("Error initializing roles and permissions", e);
        }
    }

    private void loadRoles() {
        String roleName = "SUPERUSER";
        if (roleRepository.existsByName(roleName))
            return;

        Role role = new Role();
        Set<Permission> permissions = new HashSet<>();
        IterableUtils.arrayToSet(EPermission.values()).forEach((name) -> {
            permissions.add(permissionRepository.findByName(name));
        });
        role.setName(roleName);
        role.setPermissions(permissions);
        role.setDescription("Super user");
        roleRepository.save(role);
    }

    private void loadPermissions() {
        for (EPermission name : EPermission.values()) {
            if (!permissionRepository.existsByName(name)) {
                Permission permission = new Permission();
                permission.setName(name);
                permissionRepository.save(permission);
            }
        }
    }
}
