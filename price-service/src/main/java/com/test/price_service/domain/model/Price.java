package com.test.price_service.domain.model;

import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record Price(
        @NonNull UUID priceId,
        @NonNull Long brandId,
        @NonNull DateRange dateRange,
        @NonNull Integer priceList,
        @NonNull Long productId,
        @NonNull  Integer priority,
        @NonNull Money price
) {
    public boolean isApplicable(LocalDateTime applicationDate) {
        return dateRange.contains(applicationDate);
    }


}
