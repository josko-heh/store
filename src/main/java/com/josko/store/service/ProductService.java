package com.josko.store.service;

import com.josko.store.presentation.dto.ProductCreateDto;
import com.josko.store.presentation.dto.ProductResponseDto;

import java.util.Optional;

public interface ProductService {

	Long createProduct(ProductCreateDto dto);
	
	Optional<ProductResponseDto> getProduct(Long id);
	
}
