package com.josko.store.presentation.controller;

import com.josko.store.presentation.dto.ProductCreateDto;
import com.josko.store.presentation.dto.ProductResponseDto;
import com.josko.store.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
public class ProductsController implements ProductsApi {
	
	private final ProductService service;
	
	
	@Override
	public ResponseEntity<ProductResponseDto> createProduct(ProductCreateDto productCreateDto) {

		var created = service.createProduct(productCreateDto);
		
		try {
			return ResponseEntity.created(new URI("vla"))
					.body(new ProductResponseDto());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
