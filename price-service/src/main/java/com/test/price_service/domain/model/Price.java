package com.test.price_service.domain.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

public record Price(
        UUID priceId,
        Long brandId,
        DateRange dateRange,
        Integer priceList,
        Long productId,
        Integer priority,
        Money price
) {
    public boolean isApplicable(LocalDateTime applicationDate) {
        return dateRange.contains(applicationDate);
    }
}
