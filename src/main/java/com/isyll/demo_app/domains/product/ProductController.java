package com.isyll.demo_app.domains.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isyll.demo_app.common.BaseController;
import com.isyll.demo_app.common.payload.ApiResponse;
import com.isyll.demo_app.i18n.I18nUtil;

@RestController
@RequestMapping("/products")
public class ProductController extends BaseController<Product> {

	@Autowired
	I18nUtil i18nUtil;

	@Autowired
	private ProductService productService;

	@GetMapping("/id/{id:\\d+}")
	public ResponseEntity<ApiResponse<Product>> getProductById(
			@PathVariable("id") Long id) {
		Product product = productService.findProduct(id);
		return response(product);
	}

	@GetMapping("/uuid/{uuid:[a-zA-Z0-9]+}")
	public ResponseEntity<ApiResponse<Product>> getProductByUuid(
			@PathVariable("uuid") String uuid) {
		Product product = productService.findProduct(uuid);
		return response(product);
	}

	@PostMapping
	public ResponseEntity<ApiResponse<Product>> createProduct(
			@RequestBody Product product) {
		Product savedProduct = productService.createProduct(product);
		String message = i18nUtil.getMessage("product.created");
		return response(message, savedProduct, HttpStatus.CREATED);
	}
}
