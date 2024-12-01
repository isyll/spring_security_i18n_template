package com.isyll.demo_app.domains.product;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devskiller.friendly_id.FriendlyId;
import com.isyll.demo_app.common.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public Product createProduct(Product product) {
		final Product savedProduct = productRepository.save(product);
		return savedProduct;
	}

	public Product findProduct(Long id) {
		Optional<Product> product = productRepository.findActiveById(id);
		if (!product.isPresent()) {
			throw new ResourceNotFoundException("Product not found with id: " + id);
		}
		return product.get();
	}

	public Product findProduct(String friendlyId) {
		String uuid = FriendlyId.toUuid(friendlyId).toString();
		Optional<Product> product = productRepository.findActiveByUuid(uuid);
		if (!product.isPresent()) {
			throw new ResourceNotFoundException("Product not found with uuid: " + friendlyId);
		}
		return product.get();
	}

}
