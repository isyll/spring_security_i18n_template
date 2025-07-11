package com.example.demo.repository;

import com.example.demo.model.School;
import com.example.demo.model.Teacher;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

  long countBySchool(School school);

  Optional<Teacher> findByEmail(String email);

  Optional<Teacher> findByRegistrationNumber(String registrationNumber);
}
