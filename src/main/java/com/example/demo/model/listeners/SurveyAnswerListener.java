package com.example.demo.model.listeners;

import com.example.demo.model.SurveyAnswer;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class SurveyAnswerListener {

  @PrePersist
  @PreUpdate
  public void validateFormConsistency(SurveyAnswer answer) {
    if (answer.getQuestion().getSurveyForm() != answer.getSurveyResponse().getSurveyForm()) {
      throw new IllegalStateException("Question and response must belong to the same form");
    }
  }
}
