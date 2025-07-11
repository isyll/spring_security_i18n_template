package com.example.demo.repository;

import com.example.demo.model.School;
import com.example.demo.model.Student;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

  long countBySchool(School school);

  Optional<Student> findByEmail(String email);

  Optional<?> findByRegistrationNumber(String registrationNumber);
}
