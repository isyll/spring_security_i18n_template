package com.example.demo.model;

import com.example.demo.utils.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
  "public_id",
  "email",
  "phone",
  "first_name",
  "last_name",
  "photo_url",
  "roles",
  "created_at",
  "updated_at"
})
public class User extends BaseEntity {

  @JsonIgnore private String password;

  private String email;

  private String phone;

  @JsonProperty("first_name")
  private String firstName;

  @JsonProperty("last_name")
  private String lastName;

  @JsonProperty(value = "photo_url")
  private String photoUrl;

  @JsonIgnore private AccountStatus status = AccountStatus.ACTIVE;

  @JsonProperty(value = "created_at", access = JsonProperty.Access.READ_ONLY)
  private ZonedDateTime createdAt = DateTimeUtils.getCurrentTimestamp();

  @JsonProperty(value = "updated_at", access = JsonProperty.Access.READ_ONLY)
  private ZonedDateTime updatedAt = DateTimeUtils.getCurrentTimestamp();

  private Set<String> roles = new HashSet<>();

  @JsonGetter("roles")
  public List<String> getRoleName() {
    return new ArrayList<>(roles);
  }

  public void updateTimestamps() {
    this.updatedAt = DateTimeUtils.getCurrentTimestamp();
    if (this.createdAt == null) {
      this.createdAt = this.updatedAt;
    }
  }
}
