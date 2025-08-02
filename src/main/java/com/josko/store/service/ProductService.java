package com.josko.store.service;

import com.josko.store.jpa.entity.ProductEntity;
import com.josko.store.presentation.dto.ProductCreateDto;

public interface ProductService {

	ProductEntity createProduct(ProductCreateDto dto);
}
