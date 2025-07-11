package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.example.demo.common.contract.IdentifiableByEmail;
import com.example.demo.constants.AppMetadata;
import com.example.demo.model.base.AuditableEntity;
import com.example.demo.model.enums.AccountStatus;
import com.example.demo.model.enums.UserType;
import com.example.demo.utils.PublicId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
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
  "registration_number",
  "first_name",
  "last_name",
  "photo_url",
  "roles",
  "created_at",
  "updated_at"
})
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type")
public abstract class User extends AuditableEntity implements UserDetails, IdentifiableByEmail {

  @JsonIgnore
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonProperty("registration_number")
  @Column(name = "registration_number", length = 15, nullable = false, updatable = false)
  private String registrationNumber;

  @JsonIgnore
  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "users_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  @JsonIgnore
  @Column(nullable = false, length = 60)
  private String password;

  @Column(length = 100, nullable = false)
  @JsonProperty("first_name")
  private String firstName;

  @Column(length = 100, nullable = false)
  @JsonProperty("last_name")
  private String lastName;

  @Column(length = 2000)
  @JsonProperty(value = "photo_url")
  private String photoUrl;

  @JsonIgnore
  @Column(nullable = false, length = 31)
  @Enumerated(EnumType.STRING)
  private AccountStatus status = AccountStatus.ACTIVE;

  @JsonIgnore
  @JsonProperty(value = "deleted_at")
  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @JsonProperty(value = "last_login_at", access = JsonProperty.Access.READ_ONLY)
  @Column(name = "last_login_at")
  private LocalDateTime lastLoginAt;

  @JsonIgnore
  @Getter(AccessLevel.NONE)
  @Column(name = "must_change_password", nullable = false)
  private Boolean mustChangePassword = false;

  @JsonProperty("preferred_locale")
  @Column(name = "preferred_locale", length = 2, nullable = false)
  private Locale preferredLocale = AppMetadata.DEFAULT_LOCALE;

  @JsonProperty(value = "email_verified", access = JsonProperty.Access.READ_ONLY)
  @Column(name = "email_verified", nullable = false)
  private Boolean emailVerified = false;

  public boolean mustChangePassword() {
    return mustChangePassword;
  }

  @JsonIgnore
  public abstract School getSchool();

  @JsonIgnore
  public boolean isSuperAdmin() {
    return asSuperAdmin().isPresent();
  }

  @JsonIgnore
  public boolean isAdmin() {
    return asAdmin().isPresent();
  }

  public Optional<Admin> asAdmin() {
    return this instanceof Admin admin ? Optional.of(admin) : Optional.empty();
  }

  public Optional<SuperAdmin> asSuperAdmin() {
    return this instanceof SuperAdmin superAdmin ? Optional.of(superAdmin) : Optional.empty();
  }

  public Optional<Teacher> asTeacher() {
    return this instanceof Teacher teacher ? Optional.of(teacher) : Optional.empty();
  }

  public Optional<Student> asStudent() {
    return this instanceof Student student ? Optional.of(student) : Optional.empty();
  }

  public Optional<Staff> asStaff() {
    return this instanceof Staff staff ? Optional.of(staff) : Optional.empty();
  }

  @JsonProperty(value = "user_type")
  public String getUserType() {
    return UserType.fromInstance(this).getValue();
  }

  @JsonIgnore
  public String getFullName() {
    return firstName + " " + lastName;
  }

  @JsonIgnore
  @Override
  public String getUsername() {
    return registrationNumber;
  }

  @Override
  public abstract String getEmail();

  @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
  public String getPublicId() {
    return PublicId.encode(id);
  }

  @JsonIgnore
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (isSuperAdmin()) {
      return Set.of(new SimpleGrantedAuthority("SUPER_ADMIN"));
    }
    Set<GrantedAuthority> authorities = new HashSet<>();

    for (Role role : roles) {
      for (Permission permission : role.getPermissions()) {
        authorities.add(new SimpleGrantedAuthority(permission.getName().name()));
      }
    }
    return authorities;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public String toString() {
    return "User{"
        + "id="
        + id
        + ", publicId='"
        + getPublicId()
        + '\''
        + ", registrationNumber='"
        + registrationNumber
        + '\''
        + ", firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + ", photoUrl='"
        + photoUrl
        + '\''
        + ", status="
        + status
        + ", lastLoginAt="
        + lastLoginAt
        + ", preferredLocale="
        + preferredLocale
        + ", emailVerified="
        + emailVerified
        + ", userType='"
        + getUserType()
        + '\''
        + '}';
  }
}
