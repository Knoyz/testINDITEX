package com.test.price_service.infrastructure.adapters.out;

import com.test.price_service.domain.events.PriceUpdateEvent;
import com.test.price_service.domain.ports.out.PriceEventProducerPort;
import org.springframework.stereotype.Component;

@Component
public class PriceProducerAdapter implements PriceEventProducerPort {

    @Override
    public void sendPriceUpdateEvent(PriceUpdateEvent event) {

    }
}
