package com.example.demo.utils;

import com.example.demo.common.contract.SchoolReference;
import com.example.demo.context.SchoolContextHolder;
import com.example.demo.dto.filter.UserLookup;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.SchoolOwnershipException;
import com.example.demo.model.AcademicYear;
import com.example.demo.model.Classroom;
import com.example.demo.model.Department;
import com.example.demo.model.EducationLevel;
import com.example.demo.model.Enrollment;
import com.example.demo.model.Grade;
import com.example.demo.model.Permission;
import com.example.demo.model.Role;
import com.example.demo.model.Schedule;
import com.example.demo.model.School;
import com.example.demo.model.SchoolClass;
import com.example.demo.model.Setting;
import com.example.demo.model.Specialization;
import com.example.demo.model.Staff;
import com.example.demo.model.Student;
import com.example.demo.model.Subject;
import com.example.demo.model.SuperAdmin;
import com.example.demo.model.SurveyForm;
import com.example.demo.model.SurveyQuestion;
import com.example.demo.model.SurveyResponse;
import com.example.demo.model.Teacher;
import com.example.demo.repository.StaffRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.repository.UserRepository;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class EntityResolver {

  private static final Map<Class<?>, String> ERROR_MESSAGES =
      Map.ofEntries(
          Map.entry(AcademicYear.class, "error.academicYear.not_found"),
          Map.entry(Classroom.class, "error.classroom.not_found"),
          Map.entry(Department.class, "error.department.not_found"),
          Map.entry(EducationLevel.class, "error.edu_level_not_found"),
          Map.entry(Enrollment.class, "error.enrollment.not_found"),
          Map.entry(Grade.class, "error.grade.not_found"),
          Map.entry(Permission.class, "error.permission.not_found"),
          Map.entry(Role.class, "error.role.not_found"),
          Map.entry(Schedule.class, "error.schedule.not_found"),
          Map.entry(School.class, "error.school.not_found"),
          Map.entry(SchoolClass.class, "error.schoolClass.not_found"),
          Map.entry(Setting.class, "error.setting.not_found"),
          Map.entry(Specialization.class, "error.specialization.not_found"),
          Map.entry(Staff.class, "error.staff.not_found"),
          Map.entry(Student.class, "error.student.not_found"),
          Map.entry(Subject.class, "error.subject.not_found"),
          Map.entry(SuperAdmin.class, "error.superAdmin.not_found"),
          Map.entry(SurveyForm.class, "error.surveyForm.not_found"),
          Map.entry(SurveyQuestion.class, "error.surveyQuestion.not_found"),
          Map.entry(SurveyResponse.class, "error.surveyResponse.not_found"),
          Map.entry(Teacher.class, "error.teacher.not_found"));

  private final Translator translator;
  private final SchoolContextHolder schoolContext;

  @SuppressWarnings("unchecked")
  public <T, ID> T resolveByPublicId(
      CrudRepository<T, ID> repository, String publicId, Class<T> clazz) {
    ID id;
    try {
      id = (ID) PublicId.decode(publicId);
    } catch (IllegalArgumentException e) {
      throw notFound(clazz);
    }
    return loadAndVerifyOwnership(repository, id, clazz);
  }

  @SuppressWarnings("unchecked")
  public <T, ID> T resolveFromLookup(
      CrudRepository<T, ID> repository, UserLookup lookup, Class<T> clazz) {
    if (StringUtils.hasText(lookup.id())) {
      ID id;
      try {
        id = (ID) PublicId.decode(lookup.id());
      } catch (ClassCastException e) {
        throw new IllegalArgumentException("Invalid ID type for " + clazz.getSimpleName(), e);
      } catch (IllegalArgumentException e) {
        throw notFound(clazz);
      }
      return loadAndVerifyOwnership(repository, id, clazz);
    }

    if (StringUtils.hasText(lookup.registrationNumber())) {
      T entity = loadByRegistrationNumber(repository, lookup.registrationNumber(), clazz);
      return verifySchoolOwnership(entity);
    }

    throw new BadRequestException(translator.t("error.lookup_missing_parameters"));
  }

  private <T, ID> T loadByRegistrationNumber(
      CrudRepository<T, ID> repository, String regNumber, Class<T> clazz) {
    Optional<?> result =
        switch (repository) {
          case TeacherRepository teacherRepo -> teacherRepo.findByRegistrationNumber(regNumber);
          case StudentRepository studentRepo -> studentRepo.findByRegistrationNumber(regNumber);
          case StaffRepository staffRepo -> staffRepo.findByRegistrationNumber(regNumber);
          case UserRepository userRepo -> userRepo.findByRegistrationNumber(regNumber);
          default ->
              throw new IllegalArgumentException(
                  "Unsupported repository type for registration number lookup: "
                      + repository.getClass().getSimpleName());
        };

    T entity = clazz.cast(result.orElseThrow(() -> notFound(clazz)));
    return verifySchoolOwnership(entity);
  }

  private <T, ID> T loadAndVerifyOwnership(
      CrudRepository<T, ID> repository, ID id, Class<T> clazz) {
    T entity = repository.findById(id).orElseThrow(() -> notFound(clazz));
    return verifySchoolOwnership(entity);
  }

  private <T> T verifySchoolOwnership(T entity) {
    School currentSchool = schoolContext.getSchool();
    if (entity instanceof SchoolReference ref) {
      if (!Objects.equals(ref.getSchool().getId(), currentSchool.getId())) {
        throw new SchoolOwnershipException();
      }
    }
    return entity;
  }

  private RuntimeException notFound(Class<?> clazz) {
    String key = ERROR_MESSAGES.getOrDefault(clazz, "error.unknown_entity_not_found");
    return new ResourceNotFoundException(translator.t(key));
  }
}
