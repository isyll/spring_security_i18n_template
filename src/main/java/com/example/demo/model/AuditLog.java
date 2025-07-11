package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.demo.model.base.SchoolOwnedEntity;
import com.example.demo.model.enums.AuditLogSeverity;
import com.example.demo.model.enums.AuditLogType;
import com.example.demo.model.listeners.AuditLogListener;
import com.example.demo.utils.PublicId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "audit_logs")
@EntityListeners({AuditLogListener.class})
public class AuditLog extends SchoolOwnedEntity {

  @JsonIgnore
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(length = 31, nullable = false, updatable = false)
  private AuditLogType type;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Enumerated(EnumType.STRING)
  @Column(length = 31, nullable = false, updatable = false)
  private AuditLogSeverity severity;

  @Column(nullable = false, updatable = false)
  private Instant timestamp = Instant.now();

  @ManyToOne(optional = false)
  @JoinColumn(name = "performed_by", nullable = false, updatable = false)
  private User performedBy;

  @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
  public String getPublicId() {
    return PublicId.encode(id);
  }
}
