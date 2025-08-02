package com.josko.store.service.impl;

import com.josko.store.jpa.entity.ProductEntity;
import com.josko.store.presentation.dto.ProductCreateDto;
import com.josko.store.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
	
	
	@Override
	public ProductEntity createProduct(ProductCreateDto dto) {
		return ProductEntity.builder().build();
	}
}
