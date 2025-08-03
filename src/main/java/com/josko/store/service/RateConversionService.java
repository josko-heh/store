package com.josko.store.service;

import java.math.BigDecimal;
import java.util.Optional;

public interface RateConversionService {
	
	Optional<BigDecimal> toUsd(BigDecimal eur); 
}
