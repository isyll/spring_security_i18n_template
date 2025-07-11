package com.example.demo.security;

import com.example.demo.model.School;
import com.example.demo.model.User;
import com.example.demo.model.enums.EPermission;
import com.example.demo.repository.RoleRepository;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RolePermissionEvaluator implements PermissionEvaluator {

  private final RoleRepository roleRepository;

  @Override
  public boolean hasPermission(
      Authentication authentication, Object targetDomainObject, Object permission) {
    if (authentication == null || permission == null) {
      return false;
    }

    User user = (User) authentication.getPrincipal();

    if (user.isSuperAdmin() || Objects.equals(user.getRegistrationNumber(), "ROOT")) {
      return true;
    }

    School school = user.getSchool();
    if (school == null) {
      return false;
    }

    List<EPermission> permissions =
        roleRepository.findAllPermissionNamesByUserIdAndSchoolId(user.getId(), school.getId());
    return permissions.stream().map(Enum::name).anyMatch(p -> p.equals(permission.toString()));
  }

  @Override
  public boolean hasPermission(
      Authentication authentication, Serializable targetId, String targetType, Object permission) {
    return hasPermission(authentication, null, permission);
  }
}
