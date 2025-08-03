package com.josko.store.service.impl;

import com.josko.store.jpa.repository.ProductRepository;
import com.josko.store.presentation.dto.ProductCreateDto;
import com.josko.store.presentation.dto.ProductPageResponse;
import com.josko.store.presentation.dto.ProductResponseDto;
import com.josko.store.service.ProductService;
import com.josko.store.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	
	private final ProductMapper mapper;
	private final ProductRepository repository;
	
	
	@Override
	public String createProduct(ProductCreateDto dto) {

		var entity = mapper.toEntity(dto);
		
		return repository.save(entity)
				.getCode();
	}

	@Override
	public Optional<ProductResponseDto> getProduct(String code) {
		
		var productOpt = repository.findByCode(code);
		
		if(productOpt.isEmpty()) {
			return Optional.empty();
		}
		
		return productOpt.map(mapper::toResponseDto);
	}

	@Override
	public ProductPageResponse getProducts(Pageable pageable) {
		
		var page = repository.findAll(pageable);

		var dtos = page.getContent()
				.stream()
				.map(mapper::toResponseDto)
				.toList();

		var response = new ProductPageResponse();
		response.setContent(dtos);
		response.setTotalPages(page.getTotalPages());
		response.setTotalElements(page.getTotalElements());
		response.setSize(page.getSize());
		response.setNumber(page.getNumber());
		response.setFirst(page.isFirst());
		response.setLast(page.isLast());

		return response;
	}

}
