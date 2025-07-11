package com.example.demo.service;

import com.example.demo.dto.mapper.UserMapper;
import com.example.demo.dto.user.staff.CreateStaffRequest;
import com.example.demo.model.Staff;
import com.example.demo.repository.StaffRepository;
import com.example.demo.utils.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StaffService extends BaseService {

  private final StaffRepository staffRepository;
  private final UserMapper userMapper;
  private final IdGenerator idGenerator;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public Staff createStaffMember(CreateStaffRequest request) {
    Staff staffMember = userMapper.fromRequest(request);
    staffMember.setPassword(passwordEncoder.encode(request.getPassword()));
    staffMember.setRegistrationNumber(idGenerator.generateUserId());
    staffMember.setSchool(currentSchool());
    persistUsedEmailAndPhone(staffMember.getEmail(), staffMember.getPhoneNumber());
    return staffRepository.save(staffMember);
  }
}
