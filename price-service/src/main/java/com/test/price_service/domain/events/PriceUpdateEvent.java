package com.test.price_service.domain.events;

import com.test.price_service.domain.model.DateRange;
import com.test.price_service.domain.model.Money;

public record PriceUpdateEvent(
        Long brandId,
        DateRange dateRange,
        Integer priceList,
        Long productId,
        Integer priority,
        Money price

) {
}