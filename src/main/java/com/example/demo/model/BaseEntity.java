package com.example.demo.model;

import com.example.demo.utils.Base62;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
abstract class BaseEntity {

  @Id @JsonIgnore private String id = UUID.randomUUID().toString();

  @JsonProperty("id")
  public String getPublicId() {
    return Base62.encode(UUID.fromString(id));
  }

  @JsonProperty("id")
  public void setPublicId(String publicId) {
    this.id = Base62.decode(publicId).toString();
  }
}
