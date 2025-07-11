package com.example.demo.model.listeners;

import com.example.demo.model.AuditLog;
import com.example.demo.model.enums.AuditLogSeverity;
import jakarta.persistence.PrePersist;

public class AuditLogListener {

  @PrePersist
  public void applyDefaultSeverity(AuditLog event) {
    if (event.getSeverity() != null) {
      return;
    }

    event.setSeverity(
        switch (event.getType()) {
          case SUSPICIOUS_LOGIN, UNAUTHORIZED_ACCESS_ATTEMPT -> AuditLogSeverity.CRITICAL;
          case CLOSE_ACADEMIC_YEAR_EARLY, STUDENT_DELETED -> AuditLogSeverity.WARNING;
          default -> AuditLogSeverity.INFO;
        });
  }
}
