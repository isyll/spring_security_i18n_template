package com.example.demo.features.auth.models;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.example.demo.core.utils.DateTimeUtils;
import com.example.demo.features.roles.models.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
@SQLDelete(sql = "UPDATE User SET status = 'DELETED' WHERE id=?")
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
    private Set<Role> roles = new HashSet<>();

    @JsonIgnore
    @Column(nullable = false, length = 120)
    private String password;

    @Column(unique = true, nullable = false, length = 255)
    private String email;

    @Column(unique = true, nullable = false, length = 50)
    private String phone;

    @Column(nullable = false, length = 255)
    @JsonProperty("first_name")
    private String firstName;

    @Column(nullable = false, length = 255)
    @JsonProperty("last_name")
    private String lastName;

    @Column(nullable = true, unique = true, length = 255)
    @JsonProperty(value = "photo_url")
    private String photoUrl;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private AccountStatus status = AccountStatus.ACTIVE;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = updatedAt = DateTimeUtils.getCurrentTimestamp();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = DateTimeUtils.getCurrentTimestamp();
    }

}
