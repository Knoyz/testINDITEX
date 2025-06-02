package com.test.price_service.domain.ports.out;

import com.test.price_service.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepositoryPort {

    /**
     * Retrieves the applicable price for a given product and brand at a specific date.
     *
     * @param productId the ID of the product
     * @param brandId   the ID of the brand
     * @param date      the date for which to retrieve the price
     * @return the applicable price, or null if no price is found
     */
    Optional<Price> findApplicablePrice(Long productId, Long brandId, LocalDateTime date);
}
