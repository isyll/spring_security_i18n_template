package com.example.demo.repos;

import com.example.demo.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

  boolean existsByName(String role);

  Role findRoleById(Long id);

  Role findByName(String name);

  Role save(String role);
}
