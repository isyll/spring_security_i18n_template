package com.example.demo.model;

import com.example.demo.utils.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DynamicUpdate
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET status = 'DELETED' WHERE id=?")
@SQLRestriction("status <> 'DELETED'")
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

  @JsonIgnore
  @ManyToMany
  @JoinTable(
      name = "users_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  @JsonIgnore
  @Column(nullable = false, length = 60)
  private String password;

  @Column(unique = true, nullable = false, length = 180)
  private String email;

  @Column(unique = true, nullable = false, length = 15)
  private String phone;

  @Column(nullable = false)
  @JsonProperty("first_name")
  private String firstName;

  @Column(nullable = false)
  @JsonProperty("last_name")
  private String lastName;

  @Column(unique = true)
  @JsonProperty(value = "photo_url")
  private String photoUrl;

  @JsonIgnore
  @Enumerated(EnumType.STRING)
  private AccountStatus status = AccountStatus.ACTIVE;

  @JsonProperty(value = "created_at", access = JsonProperty.Access.READ_ONLY)
  @Column(name = "created_at", nullable = false)
  private ZonedDateTime createdAt;

  @JsonProperty(value = "updated_at", access = JsonProperty.Access.READ_ONLY)
  @Column(name = "updated_at", nullable = false)
  private ZonedDateTime updatedAt;

  @PrePersist
  public void onCreate() {
    createdAt = updatedAt = DateTimeUtils.getCurrentTimestamp();
  }

  @PreUpdate
  public void onUpdate() {
    updatedAt = DateTimeUtils.getCurrentTimestamp();
  }

  @JsonGetter("roles")
  public List<String> getRoleName() {
    return roles.stream().map(Role::getName).toList();
  }
}
