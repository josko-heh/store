package com.josko.store.presentation.controller;

import com.josko.store.presentation.dto.ProductCreateDto;
import com.josko.store.presentation.dto.ProductResponseDto;
import com.josko.store.service.ProductService;
import com.josko.store.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductsController implements ProductsApi {
	
	private final ProductService service;
	private final ProductMapper mapper;
	
	
	@Override
	public ResponseEntity<Void> createProduct(ProductCreateDto productCreateDto) {

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
		
		return service.getProduct(id)
				.map(mapper::toResponseDto)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	} 
	
}
