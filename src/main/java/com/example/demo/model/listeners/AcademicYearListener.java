package com.example.demo.model.listeners;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.model.AcademicYear;
import com.example.demo.repository.AcademicYearRepository;
import com.example.demo.utils.BeanContextUtils;
import com.example.demo.utils.Translator;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AcademicYearListener {

  private Translator translator;
  private AcademicYearRepository yearRepository;

  private void initIfNeeded() {
    if (translator == null) {
      translator = BeanContextUtils.getBean(Translator.class);
    }
    if (yearRepository == null) {
      yearRepository = BeanContextUtils.getBean(AcademicYearRepository.class);
    }
  }

  @PrePersist
  public void beforeCreate(AcademicYear year) {
    initIfNeeded();
    checkOverlap(null, year);
    generateName(year);
  }

  @PreUpdate
  public void beforeUpdate(AcademicYear year) {
    initIfNeeded();
    checkOverlap(year.getId(), year);
    generateName(year);
  }

  private void checkOverlap(Long idToExclude, AcademicYear year) {
    boolean overlaps =
        (idToExclude == null)
            ? yearRepository.existsOverlappingAcademicYear(year.getStartDate(), year.getEndDate())
            : yearRepository.existsOverlappingAcademicYearExceptId(
                idToExclude, year.getStartDate(), year.getEndDate());

    if (overlaps) {
      throw new BadRequestException(translator.t("error.academicYear.overlap"));
    }
  }

  private void generateName(AcademicYear year) {
    int start = year.getStartDate().getYear();
    int end = year.getEndDate().getYear();
    year.setName(start == end ? String.valueOf(start) : start + "-" + end);
  }
}
