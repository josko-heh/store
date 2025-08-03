package com.josko.store.presentation.handler;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {


	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<String> handleValidationException(ConstraintViolationException ex) {

		var errors = ex.getConstraintViolations().stream()
				.map(v -> v.getPropertyPath() + ": " + v.getMessage())
				.collect(Collectors.joining("; "));

		return ResponseEntity.badRequest().body("Validation failed: " + errors);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<String> handleValidationException(DataIntegrityViolationException ex) {

		var message = "Data integrity violation: " + ex.getMostSpecificCause();
		
		return ResponseEntity.badRequest().body(message);
	}
}
