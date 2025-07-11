package com.example.demo.repository;

import com.example.demo.model.School;
import com.example.demo.model.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {

  long countBySchool(School school);
}
