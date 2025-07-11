package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.demo.common.contract.IdentifiableByPhoneNumber;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "used_phone_numbers")
public class UsedPhoneNumber implements IdentifiableByPhoneNumber {

  @JsonIgnore
  @Id
  @Column(name = "phone_number", nullable = false, unique = true, updatable = false, length = 15)
  private String phoneNumber;

  protected UsedPhoneNumber() {}

  public UsedPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber.toLowerCase();
  }
}
