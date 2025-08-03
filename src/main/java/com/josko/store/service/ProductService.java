package com.josko.store.service;

import com.josko.store.jpa.entity.ProductEntity;
import com.josko.store.presentation.dto.ProductCreateDto;

import java.util.Optional;

public interface ProductService {

	Long createProduct(ProductCreateDto dto);
	
	Optional<ProductEntity> getProduct(Long id);
	
}
