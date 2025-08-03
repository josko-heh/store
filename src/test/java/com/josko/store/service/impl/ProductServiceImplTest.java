package com.josko.store.service.impl;

import com.josko.store.jpa.entity.ProductEntity;
import com.josko.store.jpa.repository.ProductRepository;
import com.josko.store.presentation.dto.ProductPageResponse;
import com.josko.store.presentation.dto.ProductResponseDto;
import com.josko.store.service.mapper.ProductMapper;
import com.josko.store.util.ProductFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

	@Mock
	private ProductMapper mapper;

	@Mock
	private ProductRepository repository;

	@InjectMocks
	private ProductServiceImpl service;
	
	private ProductEntity entityFixture;
	private ProductResponseDto responseFixture;

	@BeforeEach
	void setUp() {
		entityFixture = ProductFixture.getEntity();
		responseFixture = ProductFixture.getResponseDto();
	}
	
	
	@Test
	void getProduct_whenFound_returnsMappedDto() {

		when(repository.findByCode(anyString())).thenReturn(Optional.of(entityFixture));
		when(mapper.toResponseDto(entityFixture)).thenReturn(responseFixture);
		
		Optional<ProductResponseDto> result = service.getProduct(ProductFixture.getCode());

		assertThat(result).contains(responseFixture);
	}

	@Test
	void getProduct_whenNotFound_returnsEmpty() {
		
		when(repository.findByCode(anyString())).thenReturn(Optional.empty());

		Optional<ProductResponseDto> result = service.getProduct(ProductFixture.getCode());

		assertThat(result).isEmpty();
	}

	@Test
	void getProducts_returnsPagedResponse() {
		
		var pageable = PageRequest.of(0, 1);
		var page = new PageImpl<>(List.of(entityFixture), pageable, 1);
		
		when(repository.findAll(pageable)).thenReturn(page);
		when(mapper.toResponseDto(entityFixture)).thenReturn(responseFixture);

		ProductPageResponse result = service.getProducts(pageable);

		assertThat(result.getContent()).containsExactly(responseFixture);
		assertThat(result.getTotalElements()).isEqualTo(1);
		assertThat(result.getTotalPages()).isEqualTo(1);
		assertThat(result.getSize()).isEqualTo(1);
		assertThat(result.getNumber()).isZero();
		assertThat(result.getFirst()).isTrue();
		assertThat(result.getLast()).isTrue();
	}
}
