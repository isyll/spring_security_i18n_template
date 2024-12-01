package com.isyll.demo_app.domains.product;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

	@Query(name = "Product.findActiveByUuid")
	Optional<Product> findActiveByUuid(String uuid);

	@Query(name = "Product.findActiveById")
	Optional<Product> findActiveById(Long id);

}
