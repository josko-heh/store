package com.josko.store.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.josko.store.service.RateConversionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class HNBRateConversionService implements RateConversionService {

	private static final String MID_MARKET_RATE_FIELD = "srednji_tecaj";

	private static final Logger logger = LoggerFactory.getLogger(HNBRateConversionService.class);

	private final ObjectMapper objectMapper;
	private final HNBRateFetchService fetchService;

	public HNBRateConversionService(HNBRateFetchService fetchService) {
		this.fetchService = fetchService;
		this.objectMapper = new ObjectMapper();
	}

	
	@Override
	public Optional<BigDecimal> toUsd(BigDecimal eur) {
		
		String response = fetchService.fetchRates();

		JsonNode root;
		 
		try {
			root = objectMapper.readTree(response);
		} catch (JsonProcessingException e) {
			logger.error("Failed to parse exchange rate from HNB.", e);
			return Optional.empty(); 
		}
		
		if (!root.isArray() || root.isEmpty()) {
			logger.error("Invalid response from HNB API: {}", root);
			return Optional.empty();
		}

		var midMarketRate = extractMidMarketRate(root);

		return Optional.of(
				eur.multiply(midMarketRate)
					.setScale(2, RoundingMode.HALF_UP));
	}


	private static BigDecimal extractMidMarketRate(JsonNode root) {
		
		var midMarketRateStr = root.get(0)
				.get(MID_MARKET_RATE_FIELD)
				.asText()
				.replace(",", ".");

		return new BigDecimal(midMarketRateStr);
	}

}
