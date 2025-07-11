package com.example.demo.model.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@RequiredArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity {

  @CreatedDate
  @JsonProperty(value = "created_at", access = JsonProperty.Access.READ_ONLY)
  @Column(name = "created_at", updatable = false, nullable = false)
  protected LocalDateTime createdAt;

  @LastModifiedDate
  @JsonProperty(value = "updated_at", access = JsonProperty.Access.READ_ONLY)
  @Column(name = "updated_at", nullable = false)
  protected LocalDateTime updatedAt;
}
