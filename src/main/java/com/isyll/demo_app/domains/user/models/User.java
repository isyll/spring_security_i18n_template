package com.isyll.demo_app.domains.user.models;

import java.time.LocalDateTime;
import java.util.Collection;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.isyll.demo_app.domains.user.enums.Gender;

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
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@DynamicUpdate
@Table(name = "users")
@SQLDelete(sql = "UPDATE User SET deleted = true WHERE id=?")
@NamedQuery(name = "User.findActiveById", query = "SELECT u FROM User u WHERE u.id = :id AND u.deleted = false")
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
	@Column(nullable = false, length = 100)
	private String password;

	@JsonIgnore
	@Transient
	@JsonProperty("password_confirm")
	private String passwordConfirm;

	@Column(unique = true, nullable = false, length = 250)
	private String email;

	@Column(unique = true, nullable = false, length = 16)
	private String e164_number;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private Gender gender;

	@Column(nullable = false, length = 250)
	@JsonProperty("first_name")
	private String firstName;

	@Column(nullable = false, length = 250)
	@JsonProperty("last_name")
	private String lastName;

	@Column(nullable = false)
	@JsonProperty("created_date")
	private LocalDateTime createdDate;

	@Column(nullable = false)
	@JsonProperty("updated_date")
	private LocalDateTime updatedDate;

	@JsonIgnore
	@Column(nullable = false)
	private boolean deleted = false;

	@PrePersist
	public void onCreate() {
		final LocalDateTime now = LocalDateTime.now();
		updatedDate = now;
		createdDate = now;

		processData();
	}

	@PreUpdate
	public void onUpdate() {
		final LocalDateTime now = LocalDateTime.now();
		updatedDate = now;

		processData();
	}

	private void processData() {
		// setPhone(getPhone().replaceAll("\\s", ""));
		// setFirstname(getFirstname().trim().replaceAll("\\s+", " "));
		// setLastname(getLastname().trim().replaceAll("\\s+", " "));
	}
}
