package com.test.price_service.domain.ports.in;

import com.test.price_service.domain.model.Price;

public interface PriceServicePort {
    /**
     * Retrieves the applicable price for a given product and brand at a specific date.
     *
     * @param productId the ID of the product
     * @param brandId   the ID of the brand
     * @param date      the date for which to retrieve the price
     * @return the applicable price, or null if no price is found
     */
    Price getApplicablePrice(Long productId, Long brandId, String date);
}
