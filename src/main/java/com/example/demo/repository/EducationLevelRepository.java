package com.example.demo.repository;

import com.example.demo.model.EducationLevel;
import com.example.demo.model.School;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationLevelRepository extends JpaRepository<EducationLevel, Long> {

  List<EducationLevel> findBySchool(School school);
}
