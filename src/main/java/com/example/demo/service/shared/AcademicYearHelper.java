package com.example.demo.service.shared;

import com.example.demo.context.SchoolContextHolder;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.AcademicYear;
import com.example.demo.model.School;
import com.example.demo.repository.AcademicYearRepository;
import com.example.demo.utils.Translator;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AcademicYearHelper {

  private final AcademicYearRepository yearRepository;
  private final Translator translator;
  private final SchoolContextHolder schoolContextHolder;

  private School currentSchool() {
    return schoolContextHolder.getSchool();
  }

  public Optional<AcademicYear> getCurrentAcademicYear() {
    LocalDate today = LocalDate.now();
    return yearRepository.findFirstBySchoolAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
        currentSchool(), today, today);
  }

  public AcademicYear getCurrentAcademicYearOrThrow() {
    return getCurrentAcademicYear()
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    translator.t("error.academicYear.no_active_year_found")));
  }

  public void checkStatus(AcademicYear year) {
    if (!year.canOperate()) {
      String messageKey =
          switch (year.getStatus()) {
            case CLOSED -> "error.academicYear.closed";
            case ARCHIVED -> "error.academicYear.archived";
            case OPENED, PLANNED ->
                throw new IllegalStateException(
                    "Operation should be allowed when OPENED or PLANNED");
          };

      throw new BadRequestException(translator.t(messageKey));
    }
  }
}
