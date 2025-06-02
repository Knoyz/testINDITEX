package com.test.price_service.domain.model;

import java.time.LocalDateTime;

public record DateRange(
        LocalDateTime startDate,
        LocalDateTime endDate) {

    public DateRange {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start and end dates must not be null");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }
    }

    public boolean contains(LocalDateTime date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

}
