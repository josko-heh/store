package com.josko.store.util;

import com.josko.store.jpa.entity.ProductEntity;
import com.josko.store.presentation.dto.ProductCreateDto;
import com.josko.store.presentation.dto.ProductResponseDto;

import java.math.BigDecimal;

public class ProductFixture {

	private static final String codeFixture = "ABC1234567";

	private static final String name = "Test Product";
	private static final BigDecimal priceEur = BigDecimal.valueOf(10.00);
	private static final Boolean isAvailable = true;
	
	public static ProductEntity getEntity() {

		var entityFixture = new ProductEntity();
		entityFixture.setCode(codeFixture);
		entityFixture.setName(name);
		entityFixture.setPriceEur(priceEur);
		entityFixture.setIsAvailable(isAvailable);
		
		return entityFixture;
	}

	public static ProductResponseDto getResponseDto() {

		var responseFixture = new ProductResponseDto();
		responseFixture.setCode(codeFixture);
		responseFixture.setName(name);
		responseFixture.setPriceEur(priceEur);
		responseFixture.setIsAvailable(isAvailable);
		responseFixture.setPriceUsd(BigDecimal.valueOf(11.00));
		
		return responseFixture;
	}

	public static ProductCreateDto getCreateDto() {

		var createDto = new ProductCreateDto();
		createDto.setCode(codeFixture);
		createDto.setName(name);
		createDto.setPriceEur(priceEur);
		createDto.setIsAvailable(isAvailable);
		
		return createDto;
	}


	public static String getCode() {
		return codeFixture;
	}
}
