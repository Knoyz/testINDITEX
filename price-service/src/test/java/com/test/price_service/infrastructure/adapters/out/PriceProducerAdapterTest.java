package com.test.price_service.infrastructure.adapters.out;

import com.test.price_service.domain.events.PriceUpdateEvent;
import com.test.price_service.domain.model.DateRange;
import com.test.price_service.domain.model.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class PriceProducerAdapterTest {

    @Test
    @DisplayName("Test sending PriceUpdateEvent to Kafka")
    public void testSendPriceUpdateEvent() {

        KafkaTemplate<String, PriceUpdateEvent> kafkaTemplate = mock(KafkaTemplate.class);

        CompletableFuture<SendResult<String, PriceUpdateEvent>> future = new CompletableFuture<>();
        future.complete(null);
        when(kafkaTemplate.send(anyString(), any(PriceUpdateEvent.class))).thenReturn(future);

        PriceProducerAdapter priceProducerAdapter = new PriceProducerAdapter(kafkaTemplate);
        PriceUpdateEvent event = new PriceUpdateEvent(
                1L,
                new DateRange(
                        LocalDateTime.parse("2023-01-01T00:00:00"),
                        LocalDateTime.parse("2023-12-31T23:59:59")),
                1,
                35455L,
                1,
                new Money(new BigDecimal(35.00), "EUR"));

        priceProducerAdapter.sendPriceUpdateEvent(event);
        verify(kafkaTemplate, times(1)).send("updated-price-event", event);
    }

}
