package com.test.price_service.application;

import com.test.price_service.domain.events.PriceUpdateEvent;
import com.test.price_service.domain.model.DateRange;
import com.test.price_service.domain.model.Money;
import com.test.price_service.domain.model.Price;
import com.test.price_service.domain.ports.in.PriceServicePort;
import com.test.price_service.infrastructure.adapters.in.rest.exception.PriceValidationException;
import com.test.price_service.infrastructure.adapters.out.PriceProducerAdapter;
import com.test.price_service.infrastructure.adapters.out.PriceRepositoryAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

@Slf4j
@RequiredArgsConstructor
@Service
public class PriceServiceImpl implements PriceServicePort {

    private final PriceRepositoryAdapter priceRepositoryAdapter;
    private final PriceProducerAdapter priceProducerAdapter;

    @Override
    public Price getApplicablePrice(Long productId, Long brandId, String date) {

        var x = productId;
        var y = brandId;
        var z = date;

        var dateFormatted = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss"));

        var price = priceRepositoryAdapter.findApplicablePrice(
                productId,
                brandId,
                dateFormatted
                )
                .stream()
                .filter(priceEntity -> priceEntity.dateRange() != null
                        && priceEntity.dateRange().startDate() != null
                        && priceEntity.dateRange().startDate().isBefore(dateFormatted)
                        && priceEntity.dateRange().endDate().isAfter(dateFormatted))
                .max(Comparator.comparingInt(priceEntity -> priceEntity.priority()))
                .map(priceEntity -> new Price(
                        priceEntity.priceId(),
                        priceEntity.brandId(),
                        new DateRange(priceEntity.dateRange().startDate(), priceEntity.dateRange().endDate()),
                        priceEntity.priceList(),
                        priceEntity.productId(),
                        priceEntity.priority(),
                        new Money(priceEntity.price().amount(), priceEntity.price().currency())
                ))
                .orElseThrow(
                        () -> new PriceValidationException(
                                "No applicable price found for productId: " + productId
                                        + ", brandId: " + brandId
                                        + ", date: " + date));

        log.info( "Found applicable price: {} for productId: {}, brandId: {}, date: {}",
                price, productId, brandId, date);


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
