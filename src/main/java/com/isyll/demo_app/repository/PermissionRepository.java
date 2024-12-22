package com.isyll.agrotrade.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.isyll.agrotrade.models.EPermission;
import com.isyll.agrotrade.models.Permission;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, Long> {

    Optional<Permission> findByName(EPermission name);

}
