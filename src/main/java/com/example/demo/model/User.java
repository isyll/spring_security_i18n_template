package com.example.demo.model;

import com.example.demo.model.base.AuditableEntity;
import com.example.demo.utils.Base62;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
  "id",
  "email",
  "phone",
  "first_name",
  "last_name",
  "photo_url",
  "roles",
  "created_at",
  "updated_at"
})
public class User extends AuditableEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @JsonIgnore
  private UUID id;

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
  @Column(nullable = false, length = 20)
  @Enumerated(EnumType.STRING)
  private AccountStatus status = AccountStatus.ACTIVE;

  @JsonIgnore
  @JsonProperty(value = "deleted_at")
  @Column(name = "deleted_at")
  private ZonedDateTime deletedAt;

  @JsonProperty("id")
  public String getPublicId() {
    return Base62.encode(id);
  }

  @JsonProperty("id")
  public void setPublicId(String publicId) {
    this.id = Base62.decode(publicId);
  }

  @JsonGetter("roles")
  public List<String> getRoleName() {
    return roles.stream().map(Role::getName).toList();
  }
}
