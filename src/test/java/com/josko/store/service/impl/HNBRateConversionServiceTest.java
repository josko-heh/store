package com.josko.store.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HNBRateConversionServiceTest {

	@Mock
	private HNBRatesFetchService fetchService;
	
	@InjectMocks
	private HNBRateConversionService rateConversionService;


	@Test
	void shouldReturnConvertedUsdValue_whenValidResponse() {
		
		var validJson = """
			[{"broj_tecajnice":"150","datum_primjene":"2025-08-04","drzava":"SAD","drzava_iso":"USA",
			"kupovni_tecaj":"1,142100","prodajni_tecaj":"1,138700","sifra_valute":"840",
			"srednji_tecaj":"1,140040","valuta":"USD"}]
		""";

		when(fetchService.fetchRates()).thenReturn(validJson);

		Optional<BigDecimal> result = rateConversionService.toUsd(BigDecimal.valueOf(10));

		assertTrue(result.isPresent());
		assertEquals(BigDecimal.valueOf(11.40).setScale(2), result.get());
	}

	@Test
	void shouldReturnEmpty_whenJsonIsMalformed() {
		
		var malformedJson = "not-json";

		when(fetchService.fetchRates()).thenReturn(malformedJson);

		Optional<BigDecimal> result = rateConversionService.toUsd(BigDecimal.valueOf(10));

		assertTrue(result.isEmpty());
	}

	@Test
	void shouldReturnEmpty_whenJsonArrayIsEmpty() {
		
		var emptyArrayJson = "[]";

		when(fetchService.fetchRates()).thenReturn(emptyArrayJson);

		Optional<BigDecimal> result = rateConversionService.toUsd(BigDecimal.valueOf(10));

		assertTrue(result.isEmpty());
	}

	@Test
	void shouldReturnEmpty_whenJsonIsNotArray() {
		
		var invalidJson = """
			{"srednji_tecaj": "1,140400"}
		""";

		when(fetchService.fetchRates()).thenReturn(invalidJson);

		Optional<BigDecimal> result = rateConversionService.toUsd(BigDecimal.valueOf(10));

		assertTrue(result.isEmpty());
	}
}
