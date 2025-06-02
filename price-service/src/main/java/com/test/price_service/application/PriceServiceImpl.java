package com.test.price_service.application;

import com.test.price_service.application.dtos.PriceResponse;
import com.test.price_service.domain.events.PriceUpdateEvent;
import com.test.price_service.domain.model.Price;
import com.test.price_service.domain.ports.in.PriceServicePort;
import com.test.price_service.domain.ports.out.PriceEventProducerPort;
import com.test.price_service.domain.ports.out.PriceRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class PriceServiceImpl implements PriceServicePort {

    private final PriceRepositoryPort priceRepositoryPort;
    private final PriceEventProducerPort priceEventProducerPort;

    @Override
    public Price getApplicablePrice(Long productId, Long brandId, String date) {
        var localDateTime = LocalDateTime.parse(date);

        var price = priceRepositoryPort.findApplicablePrice(productId, brandId, localDateTime).orElseThrow(() -> new IllegalArgumentException("No applicable price found for productId: " + productId + ", brandId: " + brandId + ", date: " + date));

        priceEventProducerPort.sendPriceUpdateEvent(
                new PriceUpdateEvent(
                        price.brandId(),
                        price.dateRange(),
                        price.priceList(),
                        price.productId(),
                        price.priority(),
                        price.price()));

        return price;

    }
}
