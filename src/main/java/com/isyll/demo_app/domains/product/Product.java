package com.isyll.demo_app.domains.product;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.isyll.demo_app.common.utils.json.Base62ToUuidDeserializer;
import com.isyll.demo_app.common.utils.json.UuidToBase62Serializer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
@SQLDelete(sql = "UPDATE products SET deleted = true WHERE id = ?")
@NamedQuery(name = "Product.findActiveByUuid", query = "SELECT p FROM Product p WHERE p.uuid = :uuid AND p.deleted = false")
@NamedQuery(name = "Product.findActiveById", query = "SELECT p FROM Product p WHERE p.id = :id AND p.deleted = false")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	@JsonSerialize(using = UuidToBase62Serializer.class)
	@JsonDeserialize(using = Base62ToUuidDeserializer.class)
	private String uuid;

	@Column(nullable = false)
	@Size(max = 250, message = "Name must be less than 250 characters")
	@NotBlank
	private String name;

	@Column(nullable = false, columnDefinition = "TEXT")
	@Size(max = 1000, message = "Description must be less than 1000 characters")
	@NotBlank
	private String description;

	@Column(nullable = false)
	private double price;

	@Column(nullable = false, updatable = false)
	@JsonProperty("created_date")
	private LocalDateTime createdDate;

	@Column(nullable = false)
	@JsonProperty("updated_date")
	private LocalDateTime updatedDate;

	@Column(nullable = false)
	@JsonIgnore
	private boolean deleted = false;

	@PrePersist
	protected void onCreate() {
		final LocalDateTime now = LocalDateTime.now();

		createdDate = now;
		updatedDate = now;
	}

	@PreUpdate
	protected void onUpdate() {
		updatedDate = LocalDateTime.now();
	}

}
