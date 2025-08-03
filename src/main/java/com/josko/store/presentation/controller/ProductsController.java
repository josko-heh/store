package com.josko.store.presentation.controller;

import com.josko.store.presentation.dto.ProductCreateDto;
import com.josko.store.presentation.dto.ProductPageResponse;
import com.josko.store.presentation.dto.ProductResponseDto;
import com.josko.store.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductsController implements ProductsApi {

	private static final Logger logger = LoggerFactory.getLogger(ProductsController.class);
	
	private final ProductService service;
	
	
	@Override
	public ResponseEntity<Void> createProduct(ProductCreateDto productCreateDto) {

		logger.debug("Creating product {}", productCreateDto);
		
		var id = service.createProduct(productCreateDto);

		var location = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(ProductsController.class)
						.getProduct(id))
				.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@Override
	public ResponseEntity<ProductResponseDto> getProduct(Long id) {
		
		logger.debug("Retrieving product {}", id);
		
		return service.getProduct(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@Override
	public ResponseEntity<ProductPageResponse> getProducts(Pageable pageable) {
		
		logger.debug("Retrieving products with pageable {}", pageable);
		
		var products = service.getProducts(pageable);
		
		if (products.getContent().isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(products);
	}

}
