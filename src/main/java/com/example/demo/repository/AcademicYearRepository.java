package com.example.demo.repository;

import com.example.demo.model.AcademicYear;
import com.example.demo.model.School;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicYearRepository
    extends JpaRepository<AcademicYear, Long>, JpaSpecificationExecutor<AcademicYear> {

  @Query(
      """
        SELECT COUNT(a) > 0
        FROM AcademicYear a
        WHERE a.startDate <= :endDate
            AND a.endDate >= :startDate
      """)
  boolean existsOverlappingAcademicYear(
      @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

  @Query(
      """
        SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END
        FROM AcademicYear a
        WHERE a.id <> :id
        AND a.startDate <= :endDate
        AND a.endDate >= :startDate
      """)
  boolean existsOverlappingAcademicYearExceptId(
      @Param("id") Long id,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate);

  Optional<AcademicYear> findFirstBySchoolAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
      School school, LocalDate startDate, LocalDate endDate);
}
