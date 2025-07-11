package com.example.demo.model.listeners;

import com.example.demo.model.School;
import com.example.demo.utils.BeanContextUtils;
import com.example.demo.utils.IdGenerator;
import jakarta.persistence.PrePersist;

public class SchoolListener {

  private IdGenerator idGenerator;

  private void initIfNeeded() {
    if (idGenerator == null) {
      idGenerator = BeanContextUtils.getBean(IdGenerator.class);
    }
  }

  @PrePersist
  public void preSaveValidation(School school) {
    initIfNeeded();
  }
}
