package com.josko.store.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HNBRateFetchService {

	private static final String RATES_CACHE = "rates";
	
	private static final Logger logger = LoggerFactory.getLogger(HNBRateFetchService.class);

	private final RestTemplate restTemplate;
	private final String hnbApiUrl;

	public HNBRateFetchService(RestTemplate restTemplate, @Value("${rates.hnb.url}") String hnbApiUrl) {
		this.restTemplate = restTemplate;
		this.hnbApiUrl = hnbApiUrl;
	}
	

	@Cacheable(RATES_CACHE)
	public String fetchRates() {

		logger.debug("Fetching rate conversion.");

		return restTemplate.getForObject(hnbApiUrl, String.class);
	}

	@Scheduled(cron = "${rates.cacheClear.cron}")
	@CacheEvict(value = RATES_CACHE, allEntries = true)
	public void clearCache() {
		logger.debug("Clearing rates cache.");
	}
}
