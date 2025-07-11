package com.example.demo.repository;

import com.example.demo.model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, String> {

  boolean existsByCode(String code);

  boolean existsByPhoneNumber(String phone);

  boolean existsByCodeAndIdNot(String code, String id);

  boolean existsByPhoneNumberAndIdNot(String phoneNumber, String id);

  boolean existsByEmailAndIdNot(String email, String id);

  boolean existsByEmail(String email);
}
