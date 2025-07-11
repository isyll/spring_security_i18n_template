package com.example.demo.service;

import com.example.demo.model.AuditLog;
import com.example.demo.model.User;
import com.example.demo.model.enums.AuditLogType;
import com.example.demo.repository.AuditLogRepository;
import com.example.demo.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditLogService {

  private final AuditLogRepository auditLogRepository;

  public AuditLog logEvent(AuditLogType type) {
    User currentUser = SecurityUtils.getCurrentUser();

    AuditLog auditLog = new AuditLog();
    auditLog.setType(type);
    auditLog.setPerformedBy(currentUser);

    return auditLogRepository.save(auditLog);
  }
}
