package com.test.price_service.application.dtos;

public record PriceRequest(
        Long productId,
        Long brandId,
        String applicationDate
) {

}
