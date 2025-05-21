package com.example.demo.model;

import com.example.demo.utils.Base62;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.util.UUID;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @JsonIgnore
  private UUID id;

  @JsonProperty("public_id")
  public String getPublicId() {
    return Base62.encode(id);
  }

  @JsonProperty("public_id")
  public void setPublicId(String publicId) {
    this.id = Base62.decode(publicId);
  }
}
