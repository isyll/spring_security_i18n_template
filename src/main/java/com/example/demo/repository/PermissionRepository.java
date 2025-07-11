package com.example.demo.repository;

import com.example.demo.model.Permission;
import com.example.demo.model.enums.EPermission;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, EPermission> {

  @Query("SELECT p.name FROM Permission p")
  Set<EPermission> findAllPermissionNames();
}
