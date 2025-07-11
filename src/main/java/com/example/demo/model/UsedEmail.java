package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.demo.common.contract.IdentifiableByEmail;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "used_emails")
public class UsedEmail implements IdentifiableByEmail {

  @JsonIgnore
  @Id
  @Column(name = "email", nullable = false, unique = true, updatable = false, length = 180)
  private String email;

  protected UsedEmail() {}

  public UsedEmail(String email) {
    this.email = email.toLowerCase();
  }
}
