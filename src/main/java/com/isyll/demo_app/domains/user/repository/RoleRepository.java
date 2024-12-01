package com.isyll.demo_app.domains.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isyll.demo_app.domains.user.enums.ERole;
import com.isyll.demo_app.domains.user.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	boolean existsByName(ERole name);

}
