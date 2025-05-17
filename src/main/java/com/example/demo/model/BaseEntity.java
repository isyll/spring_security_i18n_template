package com.example.demo.model;

import com.example.demo.core.utils.Base62;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @JsonIgnore
  public UUID getId() {
    return id;
  }

  @JsonIgnore
  public void setId(UUID id) {
    this.id = id;
  }

  @JsonProperty("id")
  public String getPublicId() {
    return Base62.encode(id);
  }

  @JsonProperty("id")
  public void setPublicId(String publicId) {
    this.id = Base62.decode(publicId);
  }
}
