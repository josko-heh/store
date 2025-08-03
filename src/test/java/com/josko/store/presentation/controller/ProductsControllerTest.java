package com.josko.store.presentation.controller;

import com.josko.store.presentation.dto.ProductCreateDto;
import com.josko.store.presentation.dto.ProductPageResponse;
import com.josko.store.presentation.dto.ProductResponseDto;
import com.josko.store.service.ProductService;
import com.josko.store.util.ProductFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductsControllerTest {

	@Mock
	private ProductService service;
	
	@InjectMocks
	private ProductsController controller;

	private String codeFixture;
	private ProductCreateDto createDtoFixture;
	private ProductResponseDto responseDtoFixture;

	@BeforeEach
	void setUp() {
		codeFixture = ProductFixture.getCode();
		createDtoFixture = ProductFixture.getCreateDto();
		responseDtoFixture = ProductFixture.getResponseDto();
	}

	@Test
	void createProduct_shouldReturn201Created() {
		
		doNothing().when(service).createProduct(any());

		var response = controller.createProduct(createDtoFixture);

		assertEquals(201, response.getStatusCode().value());
		assertNotNull(response.getHeaders().getLocation());
		verify(service).createProduct(createDtoFixture);
	}

	@Test
	void getProduct_shouldReturn200_whenProductExists() {
		
		when(service.getProduct(codeFixture)).thenReturn(Optional.of(responseDtoFixture));

		var response = controller.getProduct(codeFixture);

		assertEquals(200, response.getStatusCode().value());
		assertEquals(responseDtoFixture, response.getBody());
		verify(service).getProduct(codeFixture);
	}

	@Test
	void getProduct_shouldReturn404_whenNotFound() {
		
		when(service.getProduct(codeFixture)).thenReturn(Optional.empty());

		var response = controller.getProduct(codeFixture);

		assertEquals(404, response.getStatusCode().value());
		assertNull(response.getBody());
		verify(service).getProduct(codeFixture);
	}

	@Test
	void getProducts_shouldReturn200_whenProductsExist() {
		
		var pageable = PageRequest.of(0, 10);
		var pageResponse = new ProductPageResponse();
		pageResponse.setContent(List.of(responseDtoFixture));
		pageResponse.setTotalElements(1L);
		pageResponse.setTotalPages(1);
		pageResponse.setFirst(true);
		pageResponse.setLast(true);
		pageResponse.setSize(10);
		pageResponse.setNumber(0);

		when(service.getProducts(pageable)).thenReturn(pageResponse);

		var response = controller.getProducts(pageable);

		assertEquals(200, response.getStatusCode().value());
		assertEquals(1, response.getBody().getContent().size());
		verify(service).getProducts(pageable);
	}

	@Test
	void getProducts_shouldReturn204_whenEmpty() {
		
		var pageable = PageRequest.of(0, 10);
		var emptyResponse = new ProductPageResponse();
		emptyResponse.setContent(List.of());

		when(service.getProducts(pageable)).thenReturn(emptyResponse);

		ResponseEntity<ProductPageResponse> response = controller.getProducts(pageable);

		assertEquals(204, response.getStatusCode().value());
		verify(service).getProducts(pageable);
	}
	
}
