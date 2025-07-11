package com.example.demo.service;

import com.example.demo.dto.filter.AcademicYearQuery;
import com.example.demo.dto.mapper.AcademicYearMapper;
import com.example.demo.dto.school.AcademicYearDto;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.model.AcademicYear;
import com.example.demo.model.enums.AcademicYearStatus;
import com.example.demo.repository.AcademicYearRepository;
import com.example.demo.utils.Translator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AcademicYearService extends BaseService {

  private final AcademicYearRepository yearRepository;
  private final AcademicYearMapper yearMapper;
  private final Translator translator;

  @Transactional
  public AcademicYear createAcademicYear(AcademicYearDto yearDto) {
    AcademicYear academicYear = yearMapper.fromDto(yearDto);
    academicYear.setSchool(currentSchool());
    return yearRepository.save(academicYear);
  }

  public List<AcademicYear> filterAcademicYears(AcademicYearQuery queryFilter) {
    Specification<AcademicYear> spec =
        (root, query, builder) -> builder.equal(root.get("school"), currentSchool());

    if (queryFilter.name() != null && !queryFilter.name().isBlank()) {
      spec =
          spec.and(
              (root, query, cb) ->
                  cb.like(
                      cb.lower(root.get("name")), "%" + queryFilter.name().toLowerCase() + "%"));
    }

    if (queryFilter.startDateFrom() != null) {
      spec =
          spec.and(
              (root, query, cb) ->
                  cb.greaterThanOrEqualTo(root.get("startDate"), queryFilter.startDateFrom()));
    }

    if (queryFilter.startDateTo() != null) {
      spec =
          spec.and(
              (root, query, cb) ->
                  cb.lessThanOrEqualTo(root.get("startDate"), queryFilter.startDateTo()));
    }

    if (queryFilter.endDateFrom() != null) {
      spec =
          spec.and(
              (root, query, cb) ->
                  cb.greaterThanOrEqualTo(root.get("endDate"), queryFilter.endDateFrom()));
    }

    if (queryFilter.endDateTo() != null) {
      spec =
          spec.and(
              (root, query, cb) ->
                  cb.lessThanOrEqualTo(root.get("endDate"), queryFilter.endDateTo()));
    }

    if (queryFilter.status() != null) {
      spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), queryFilter.status()));
    }

    return yearRepository.findAll(spec, queryFilter.getSort());
  }

  public AcademicYear closeAcademicYear(AcademicYear year) {
    if (year.getStatus() == AcademicYearStatus.CLOSED) {
      throw new BadRequestException(translator.t("error.academicYear.already_closed"));
    }
    year.setStatus(AcademicYearStatus.CLOSED);
    return yearRepository.save(year);
  }
}
