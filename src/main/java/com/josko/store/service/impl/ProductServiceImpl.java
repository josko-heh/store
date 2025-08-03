package com.josko.store.service.impl;

import com.josko.store.jpa.entity.ProductEntity;
import com.josko.store.jpa.repository.ProductRepository;
import com.josko.store.presentation.dto.ProductCreateDto;
import com.josko.store.service.ProductService;
import com.josko.store.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	
	private final ProductMapper mapper;
	private final ProductRepository repository;
	
	
	@Override
	public ProductEntity createProduct(ProductCreateDto dto) {

		var entity = mapper.toEntity(dto);
		
		return repository.save(entity);
	}
	
}
