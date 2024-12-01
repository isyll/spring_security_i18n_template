package com.isyll.demo_app.domains.user;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isyll.demo_app.domains.user.enums.EPermission;
import com.isyll.demo_app.domains.user.enums.ERole;
import com.isyll.demo_app.domains.user.models.Permission;
import com.isyll.demo_app.domains.user.models.Role;
import com.isyll.demo_app.domains.user.repository.PermissionRepository;
import com.isyll.demo_app.domains.user.repository.RoleRepository;

import jakarta.annotation.PostConstruct;

@Component
public class RolePermissionInitializer {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PermissionRepository permissionRepository;

	private final Map<ERole, Set<EPermission>> rolePermissionsMap = new HashMap<>() {
		{
			put(ERole.ROLE_ADMIN, new HashSet<>(Arrays.asList(
					EPermission.VIEW_PRODUCTS,
					EPermission.ADD_PRODUCTS,
					EPermission.EDIT_PRODUCTS,
					EPermission.DELETE_PRODUCTS,
					EPermission.VIEW_ORDERS,
					EPermission.MANAGE_ORDERS,
					EPermission.VIEW_USERS,
					EPermission.MANAGE_USERS)));
			put(ERole.ROLE_USER, new HashSet<>(Arrays.asList(
					EPermission.VIEW_PRODUCTS,
					EPermission.ADD_PRODUCTS,
					EPermission.VIEW_ORDERS)));
		}
	};

	@PostConstruct
	public void initialize() {
		initializePermissions();
		initializeRoles();
	}

	private void initializePermissions() {
		Arrays.stream(EPermission.values()).forEach(permission -> {
			if (!permissionRepository.existsByName(permission)) {
				Permission permissionEntity = new Permission();
				permissionEntity.setName(permission);
				permissionRepository.save(permissionEntity);
			}
		});
	}

	private void initializeRoles() {
		List<Permission> permissions = permissionRepository.findAll();

		Arrays.stream(ERole.values()).forEach(role -> {
			if (!roleRepository.existsByName(role)) {
				Role roleEntity = new Role();
				Set<Permission> rolesPermissions = permissions.stream()
						.filter(p -> rolePermissionsMap.get(role).contains(p.getName()))
						.collect(Collectors.toSet());
				roleEntity.setName(role);
				roleEntity.setPermissions(rolesPermissions);
				roleRepository.save(roleEntity);
			}
		});
	}
}
