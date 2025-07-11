package com.example.demo.repository;

import com.example.demo.model.Staff;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

  Optional<Staff> findByEmail(String email);

  Optional<?> findByRegistrationNumber(String registrationNumber);
}
