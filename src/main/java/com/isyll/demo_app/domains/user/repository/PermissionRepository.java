package com.isyll.demo_app.domains.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isyll.demo_app.domains.user.enums.EPermission;
import com.isyll.demo_app.domains.user.models.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

	boolean existsByName(EPermission name);

	Optional<Permission> findByName(String name);

}
