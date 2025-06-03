package com.test.price_service.infrastructure.adapters.in.rest.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(PriceValidationException.class)
    public ResponseEntity<String> handlePriceValidationException(PriceValidationException ex) {
        log.warn("Price validation error: {}", ex.getMessage(), ex);
        return ResponseEntity.badRequest().body("Price Validation Error: " + ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        log.error("An error occurred: {}", ex.getMessage(), ex);
        return ResponseEntity.status(500).body("Internal Server Error: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        log.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        return ResponseEntity.status(500).body("Unexpected Error: " + ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("Invalid argument: {}", ex.getMessage(), ex);
        return ResponseEntity.badRequest().body("Bad Request: " + ex.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        log.error("Null pointer exception: {}", ex.getMessage(), ex);
        return ResponseEntity.status(500).body("Internal Server Error: " + ex.getMessage());
    }



}
