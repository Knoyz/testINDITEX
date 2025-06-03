package com.test.price_service.infrastructure.adapters.out;

import com.test.price_service.domain.events.PriceUpdateEvent;
import com.test.price_service.domain.ports.out.PriceEventProducerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PriceProducerAdapter implements PriceEventProducerPort {

    private final KafkaTemplate<String, PriceUpdateEvent> kafkaTemplate;

    private static final String TOPIC = "updated-price-event";

    @Override
    public void sendPriceUpdateEvent(PriceUpdateEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}
