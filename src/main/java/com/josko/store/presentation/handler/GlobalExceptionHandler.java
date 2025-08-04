package com.josko.store.presentation.handler;

import com.josko.store.presentation.dto.ValidationError;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

@RestControllerAdvice
public class GlobalExceptionHandler {


	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ValidationError> handleValidationException(ConstraintViolationException ex) {
		
		var errors = ex.getConstraintViolations().stream()
				.map(v -> v.getPropertyPath() + ": " + v.getMessage())
				.toList();

		return ResponseEntity.badRequest().body(buildValidationError(errors, "Validation failed."));
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ValidationError> handleDBValidationException(DataIntegrityViolationException ex) {
		
		var message = "Data integrity violation: " + ex.getMostSpecificCause().getMessage();

		var errors = List.of("database: " + message);

		return ResponseEntity.badRequest().body(buildValidationError(errors, "Database validation failed."));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> handleValidationErrors(MethodArgumentNotValidException ex) {
		
		var errors = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + ": " +
						(isBlank(error.getDefaultMessage()) ? "" : error.getDefaultMessage()))
				.toList();

		return ResponseEntity.badRequest().body(buildValidationError(errors, "Argument validation failed."));
	}


	private static ValidationError buildValidationError(List<String> errors, String message) {
		
		var validationError = new ValidationError();
		validationError.setErrors(errors);
		validationError.setMessage(message);

		return validationError;
	}
}
