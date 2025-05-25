package com.example.demo.model.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.ZonedDateTime;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
@Data
public abstract class AuditableEntity {
  @CreatedDate
  @JsonProperty("created_at")
  @Column(name = "created_at", updatable = false)
  private ZonedDateTime createdAt;

  @LastModifiedDate
  @JsonProperty("updated_at")
  @Column(name = "updated_at")
  private ZonedDateTime updatedAt;
}
