package com.example.demo.service;

import com.example.demo.dto.mapper.UserMapper;
import com.example.demo.dto.user.admin.CreateAdminRequest;
import com.example.demo.exceptions.UniqueConstraintViolationException;
import com.example.demo.model.Admin;
import com.example.demo.repository.AdminRepository;
import com.example.demo.utils.IdGenerator;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService extends BaseService {

  private final AdminRepository adminRepository;
  private final UserMapper userMapper;
  private final IdGenerator idGenerator;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public Admin createAdmin(CreateAdminRequest request) {
    validateUniqueFieldsOnCreate(request);
    Admin admin = userMapper.fromRequest(request);
    admin.setPassword(passwordEncoder.encode(request.getPassword()));
    admin.setRegistrationNumber(idGenerator.generateUserId());
    persistUsedEmailAndPhone(admin.getEmail(), admin.getPhoneNumber());
    return adminRepository.save(admin);
  }

  private void validateUniqueFieldsOnCreate(CreateAdminRequest request) {
    Map<String, String> errors = new HashMap<>();
    if (adminRepository.existsByEmail(request.getEmail())) {
      errors.put("email", "validation.email_already_exists");
    }
    if (adminRepository.existsByPhoneNumber(request.getPhoneNumber())) {
      errors.put("phone", "validation.phone_already_exists");
    }
    if (!errors.isEmpty()) {
      throw new UniqueConstraintViolationException(errors);
    }
  }
}
