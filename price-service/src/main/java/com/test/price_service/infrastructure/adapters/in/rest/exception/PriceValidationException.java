package com.test.price_service.infrastructure.adapters.in.rest.exception;

import org.springframework.stereotype.Component;

@Component
public class PriceValidationException extends RuntimeException {

    public PriceValidationException() {
        super("Price validation failed");
    }

    public PriceValidationException(String message) {
        super(message);
    }

    public PriceValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
