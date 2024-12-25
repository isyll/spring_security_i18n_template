package com.example.demo.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.ERole;
import com.example.demo.models.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByName(ERole name);

    Role save(ERole permission);

}
