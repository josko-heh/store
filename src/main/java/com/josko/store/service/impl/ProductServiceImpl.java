package com.josko.store.service.impl;

import com.josko.store.jpa.repository.ProductRepository;
import com.josko.store.presentation.dto.ProductCreateDto;
import com.josko.store.presentation.dto.ProductResponseDto;
import com.josko.store.service.ProductService;
import com.josko.store.service.RateConversionService;
import com.josko.store.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	
	private final ProductMapper mapper;
	private final ProductRepository repository;
	private final RateConversionService conversionService;
	
	
	@Override
	public Long createProduct(ProductCreateDto dto) {

		var entity = mapper.toEntity(dto);
		
		return repository.save(entity).getId();
	}

	@Override
	public Optional<ProductResponseDto> getProduct(Long id) {
		
		var productOpt = repository.findById(id);
		
		if(productOpt.isEmpty()) {
			return Optional.empty();
		}
		
		return productOpt.map(mapper::toResponseDto)
				.map(this::setUsdPrice);
	}

	
	private ProductResponseDto setUsdPrice(ProductResponseDto productDto) {
		
		var priceUsdOpt = conversionService.toUsd(productDto.getPriceEur());

		priceUsdOpt.ifPresent(productDto::setPriceUsd);
		
		return productDto;
	}

}
