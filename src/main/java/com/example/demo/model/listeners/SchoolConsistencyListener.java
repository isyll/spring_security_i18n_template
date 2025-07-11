package com.example.demo.model.listeners;

import com.example.demo.common.contract.SchoolReference;
import com.example.demo.exceptions.SchoolConsistencyException;
import com.example.demo.model.School;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SchoolConsistencyListener {

  @PrePersist
  @PreUpdate
  public void validateSchoolConsistency(Object entity) {
    Set<School> foundSchools = new HashSet<>();

    if (entity instanceof SchoolReference schoolOwned) {
      foundSchools.add(schoolOwned.getSchool());
    }

    for (var field : entity.getClass().getDeclaredFields()) {
      field.setAccessible(true);
      try {
        Object value = field.get(entity);

        if (value == null) {
          continue;
        }

        if (value instanceof SchoolReference schoolOwned) {
          School school = schoolOwned.getSchool();
          if (school != null) {
            foundSchools.add(school);
          }
        }

        if (value instanceof Collection<?> collection) {
          for (Object item : collection) {
            if (item instanceof SchoolReference schoolOwnedItem) {
              School school = schoolOwnedItem.getSchool();
              if (school != null) {
                foundSchools.add(school);
              }
            }
          }
        }
      } catch (IllegalAccessException e) {
        throw new RuntimeException("Can't access field: " + field.getName(), e);
      }
    }

    if (foundSchools.size() > 1) {
      throw new SchoolConsistencyException(
          "Inconsistent schools found in entity: " + entity.getClass().getSimpleName());
    }
  }
}
