package com.josko.store.service;

import com.josko.store.presentation.dto.ProductCreateDto;
import com.josko.store.presentation.dto.ProductPageResponse;
import com.josko.store.presentation.dto.ProductResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {

	String createProduct(ProductCreateDto dto);
	
	Optional<ProductResponseDto> getProduct(String code);

	ProductPageResponse getProducts(Pageable pageable);

}
