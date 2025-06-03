package com.test.price_service.application;

import com.test.price_service.application.dtos.PriceResponse;
import com.test.price_service.domain.events.PriceUpdateEvent;
import com.test.price_service.domain.model.Price;
import com.test.price_service.domain.ports.in.PriceServicePort;
import com.test.price_service.domain.ports.out.PriceEventProducerPort;
import com.test.price_service.domain.ports.out.PriceRepositoryPort;
import com.test.price_service.infrastructure.adapters.out.PriceProducerAdapter;
import com.test.price_service.infrastructure.adapters.out.PriceRepositoryAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class PriceServiceImpl implements PriceServicePort {

    private final PriceRepositoryAdapter priceRepositoryAdapter;
    private final PriceProducerAdapter priceProducerAdapter;

    @Override
    public Price getApplicablePrice(Long productId, Long brandId, String date) {
        var localDateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss"));

        var price = priceRepositoryAdapter.findApplicablePrice(
                productId,
                brandId,
                localDateTime).orElseThrow(
                        () -> new IllegalArgumentException(
                                "No applicable price found for productId: " + productId
                                        + ", brandId: " + brandId
                                        + ", date: " + date));

        priceProducerAdapter.sendPriceUpdateEvent(
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
