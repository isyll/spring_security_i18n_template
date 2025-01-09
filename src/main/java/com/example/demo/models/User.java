package com.example.demo.models;

import java.time.ZonedDateTime;
import java.util.Collection;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.demo.utils.DateTimeUtils;

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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@DynamicUpdate
@Table(name = "users")
@SQLDelete(sql = "UPDATE User SET deleted = true WHERE id=?")
@SQLRestriction("status <> 'DELETED'")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @JsonIgnore
    @Column(nullable = false, length = 120)
    private String password;

    @Column(unique = true, nullable = false, length = 250)
    private String email;

    @Column(unique = true, nullable = false, length = 30)
    private String phone;

    @Column(name = "country_code", length = 2, nullable = false)
    @JsonProperty("country_code")
    private String countryCode;

    @Column(nullable = false, length = 6)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false, length = 250)
    @JsonProperty("first_name")
    private String firstName;

    @Column(nullable = false, length = 250)
    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("date_of_birth")
    @Column(nullable = false)
    private ZonedDateTime dateOfBirth;

    @JsonIgnore
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updatedAt;

    @JsonIgnore
    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified = false;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private AccountStatus status = AccountStatus.ACTIVE;

    @PrePersist
    public void onCreate() {
        createdAt = updatedAt = DateTimeUtils.getCurrentTimestamp();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = DateTimeUtils.getCurrentTimestamp();
    }

    @JsonGetter("email_verified")
    public boolean isEmailVerified() {
        return emailVerified;
    }
}
