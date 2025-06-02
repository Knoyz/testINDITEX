package com.test.price_service.application.dtos;

import java.math.BigDecimal;

public record PriceResponse(
        Long productId,
        Long brandId,
        Integer priceList,
        String startDate,
        String endDate,
        String finalPrice
) {
}
