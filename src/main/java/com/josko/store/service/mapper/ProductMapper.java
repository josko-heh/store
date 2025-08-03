package com.josko.store.service.mapper;

import com.josko.store.jpa.entity.ProductEntity;
import com.josko.store.presentation.dto.ProductCreateDto;
import com.josko.store.presentation.dto.ProductResponseDto;
import com.josko.store.service.RateConversionService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

	@Autowired
	private RateConversionService conversionService;


	public abstract ProductEntity toEntity(ProductCreateDto dto);

	public ProductResponseDto toResponseDto(ProductEntity entity) {
		
		if (entity == null) {
			return null;
		}
		
		var dto = toResponseDtoInternal(entity);

		conversionService.toUsd(dto.getPriceEur())
				.ifPresent(dto::setPriceUsd);

		return dto;
	}

	protected abstract ProductResponseDto toResponseDtoInternal(ProductEntity entity);

}
