package com.isyll.agrotrade.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.isyll.agrotrade.models.ERole;
import com.isyll.agrotrade.models.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(ERole name);

    Role save(ERole permission);

}
