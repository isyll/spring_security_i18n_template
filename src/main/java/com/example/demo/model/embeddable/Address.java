package com.example.demo.model.embeddable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Address {

  @Column(nullable = false, length = 100)
  private String street;

  @Column(nullable = false, length = 50)
  private String city;

  @JsonProperty("postal_code")
  @Column(name = "postal_code", nullable = false, length = 12)
  private String postalCode;

  @Column(nullable = false, length = 50)
  private String country;

  @JsonIgnore
  public String getFullAddress() {
    return String.format("%s, %s %s, %s", street, postalCode, city, country);
  }

  @Override
  public String toString() {
    return getFullAddress();
  }
}
