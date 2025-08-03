package com.josko.store.service.mapper;

import com.josko.store.jpa.entity.ProductEntity;
import com.josko.store.presentation.dto.ProductCreateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	ProductEntity toEntity(ProductCreateDto dto);

}
