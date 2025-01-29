package com.example.demo.features.roles.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.features.roles.models.EPermission;
import com.example.demo.features.roles.models.Permission;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, Long> {

    Permission findByName(EPermission name);

    boolean existsByName(EPermission name);

}
