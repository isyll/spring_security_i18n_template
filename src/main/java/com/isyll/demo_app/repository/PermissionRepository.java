package com.isyll.demo_app.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.isyll.demo_app.models.EPermission;
import com.isyll.demo_app.models.Permission;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, Long> {

    Optional<Permission> findByName(EPermission name);

}
