package com.example.demo.model;

import com.example.demo.model.enums.EPermission;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "permissions")
@NoArgsConstructor
public class Permission {

  @Id
  @Enumerated(EnumType.STRING)
  @Column(length = 31, nullable = false, unique = true, updatable = false)
  private EPermission name;

  public Permission(EPermission name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Permission permission = (Permission) o;

    return Objects.equals(name, permission.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  public String toString() {
    return name.name();
  }
}
