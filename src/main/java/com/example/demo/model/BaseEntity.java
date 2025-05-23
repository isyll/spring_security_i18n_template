package com.example.demo.model;

import com.example.demo.utils.Base62;
import com.example.demo.utils.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.Data;

@MappedSuperclass
@Data
abstract class BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @JsonIgnore
  private UUID id;

  @JsonProperty(value = "created_at", access = JsonProperty.Access.READ_ONLY)
  @Column(name = "created_at", nullable = false)
  private ZonedDateTime createdAt;

  @JsonProperty(value = "updated_at", access = JsonProperty.Access.READ_ONLY)
  @Column(name = "updated_at", nullable = false)
  private ZonedDateTime updatedAt;

  @JsonProperty("id")
  public String getPublicId() {
    return Base62.encode(id);
  }

  @JsonProperty("id")
  public void setPublicId(String publicId) {
    this.id = Base62.decode(publicId);
  }

  @PrePersist
  public void onCreate() {
    createdAt = updatedAt = DateTimeUtils.getCurrentTimestamp();
  }

  @PreUpdate
  public void onUpdate() {
    updatedAt = DateTimeUtils.getCurrentTimestamp();
  }
}
