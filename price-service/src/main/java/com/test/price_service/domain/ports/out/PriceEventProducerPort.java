package com.test.price_service.domain.ports.out;

import com.test.price_service.domain.events.PriceUpdateEvent;
import com.test.price_service.domain.model.Price;

public interface PriceEventProducerPort {
    /**
     * Sends a price update event to the message broker.
     *
     * @param event the price update event to be sent
     */
    void sendPriceUpdateEvent(PriceUpdateEvent event);
}
