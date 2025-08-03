package com.josko.store.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.josko.store.service.RateConversionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class HNBRateConversionService implements RateConversionService {

	private static final String MID_MARKET_RATE_FIELD = "srednji_tecaj";

	private final RestTemplate restTemplate;
	private final String hnbApiUrl;
	private final ObjectMapper objectMapper;

	public HNBRateConversionService(RestTemplate restTemplate, @Value("${exchange.hnb.url}") String hnbApiUrl) {
		this.restTemplate = restTemplate;
		this.hnbApiUrl = hnbApiUrl;
		this.objectMapper = new ObjectMapper();
	}

	
	@Override
	public Optional<BigDecimal> toUsd(BigDecimal eur) {
		
		String response = restTemplate.getForObject(hnbApiUrl, String.class);
		
		JsonNode root;
		 
		try {
			root = objectMapper.readTree(response);
		} catch (JsonProcessingException e) {
//			todo log ("Failed to parse exchange rate from HNB.", e);
			return Optional.empty(); 
		}
		
		if (!root.isArray() || root.isEmpty()) {
//			todo log ("Invalid response from HNB API: {}", root);
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
