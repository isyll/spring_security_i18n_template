package com.example.demo.service;

import com.example.demo.constants.DefaultSettingValues;
import com.example.demo.dto.mapper.SchoolMapper;
import com.example.demo.dto.school.SchoolDto;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.UniqueConstraintViolationException;
import com.example.demo.model.Role;
import com.example.demo.model.School;
import com.example.demo.model.Setting;
import com.example.demo.model.User;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.SchoolRepository;
import com.example.demo.repository.SettingRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.IdGenerator;
import com.example.demo.utils.IterableUtils;
import com.example.demo.utils.Translator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SchoolService extends BaseService {

  private final SchoolRepository schoolRepository;
  private final SettingRepository settingRepository;
  private final PermissionRepository permissionRepository;
  private final RoleRepository roleRepository;
  private final UserRepository userRepository;
  private final AdminRepository adminRepository;
  private final SchoolMapper schoolMapper;
  private final Translator translator;
  private final IdGenerator idGenerator;

  @Transactional
  public School createSchool(SchoolDto schoolDto) {
    User user = currentUser();
    user.asAdmin()
        .ifPresent(
            admin -> {
              if (admin.getSchool() != null) {
                throw new BadRequestException(translator.t("error.admin_has_school"));
              }
            });

    School school = schoolMapper.fromDto(schoolDto);
    school.setSchoolNumber(idGenerator.generateUniqueSchoolNumber());
    school.setCreatedBy(currentUser());

    validateUniqueFieldsOnCreate(school);
    School savedSchool = schoolRepository.save(school);
    user.asAdmin()
        .ifPresent(
            admin -> {
              admin.setSchool(savedSchool);
              adminRepository.save(admin);
            });

    createDefaultSettingsForSchool(savedSchool);
    assignSchoolAdminRoleToCreator(savedSchool);
    persistUsedEmailAndPhone(school.getEmail(), school.getPhoneNumber());
    return savedSchool;
  }

  @Transactional
  public School updateSchool(SchoolDto schoolDto, School school) {
    schoolMapper.updateSchoolFromDto(schoolDto, school);
    validateUniqueFieldsOnUpdate(school);
    return schoolRepository.save(school);
  }

  private void validateUniqueFieldsOnCreate(School school) {
    Map<String, String> errors = new HashMap<>();

    if (schoolRepository.existsByCode(school.getCode())) {
      errors.put("code", translator.t("validation.school_code_already_exists"));
    }
    if (schoolRepository.existsByPhoneNumber(school.getPhoneNumber())) {
      errors.put("phone_number", translator.t("validation.school_phone_already_exists"));
    }
    if (schoolRepository.existsByEmail(school.getEmail())) {
      errors.put("email", translator.t("validation.school_email_already_exists"));
    }

    if (!errors.isEmpty()) {
      throw new UniqueConstraintViolationException(errors);
    }
  }

  private void validateUniqueFieldsOnUpdate(School school) {
    Map<String, String> errors = new HashMap<>();
    String id = school.getId();

    if (schoolRepository.existsByCodeAndIdNot(school.getCode(), id)) {
      errors.put("code", translator.t("validation.school_code_already_exists"));
    }
    if (schoolRepository.existsByPhoneNumberAndIdNot(school.getPhoneNumber(), id)) {
      errors.put("phone_number", translator.t("validation.school_phone_already_exists"));
    }
    if (schoolRepository.existsByEmailAndIdNot(school.getEmail(), id)) {
      errors.put("email", translator.t("validation.school_email_already_exists"));
    }

    if (!errors.isEmpty()) {
      throw new UniqueConstraintViolationException(errors);
    }
  }

  private void assignSchoolAdminRoleToCreator(School school) {
    User creator = school.getCreatedBy();

    Role schoolAdminRole = new Role();
    schoolAdminRole.setName(translator.t("role.school_admin.name"));
    schoolAdminRole.setDescription(translator.t("role.school_admin.description"));
    schoolAdminRole.setSchool(school);
    schoolAdminRole.setPermissions(IterableUtils.toSet(permissionRepository.findAll()));

    Role savedRole = roleRepository.save(schoolAdminRole);

    creator.getRoles().add(savedRole);
    userRepository.saveAndFlush(creator);
  }

  private void createDefaultSettingsForSchool(School school) {
    List<Setting> settings =
        Arrays.stream(DefaultSettingValues.settingValues)
            .map(
                value -> {
                  Setting setting = new Setting();
                  setting.setKey(value.key());
                  setting.setValue(value.value());
                  setting.setCategory(value.category());
                  setting.setType(value.type());
                  setting.setSchool(school);
                  return setting;
                })
            .toList();

    settingRepository.saveAll(settings);
  }
}
