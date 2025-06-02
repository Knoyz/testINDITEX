package com.test.price_service.application.dtos;

import lombok.NonNull;

public record PriceRequest(
        @NonNull
        Long productId,
        @NonNull
        Long brandId,
        @NonNull
        String applicationDate
) {

}
