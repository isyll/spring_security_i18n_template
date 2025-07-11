package com.example.demo.repository;

import com.example.demo.model.SuperAdmin;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Long> {

  Optional<SuperAdmin> findByEmail(String email);
}
