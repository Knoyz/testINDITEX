package com.test.price_service.infrastructure.adapters.out;

import com.test.price_service.domain.model.DateRange;
import com.test.price_service.domain.model.Money;
import com.test.price_service.domain.model.Price;
import com.test.price_service.domain.ports.out.PriceRepositoryPort;
import com.test.price_service.infrastructure.repository.PriceH2Repository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
public class PriceRepositoryAdapter implements PriceRepositoryPort {

    private final PriceH2Repository priceH2Repository;

    @Override
    public Optional<Price> findApplicablePrice(Long productId, Long brandId, LocalDateTime date) {
        return priceH2Repository.findAll()
                .stream()
                .filter(priceEntity -> priceEntity.getProductId().equals(productId)
                        && priceEntity.getBrandId().equals(brandId)
                        && priceEntity.getStartDate().isBefore(date)
                        && priceEntity.getEndDate().isAfter(date))
                .findFirst()
                .map(priceEntity -> new Price(
                        priceEntity.getPriceId(),
                        priceEntity.getBrandId(),
                        new DateRange(priceEntity.getStartDate(), priceEntity.getEndDate()),
                        priceEntity.getPriceList(),
                        priceEntity.getProductId(),
                        priceEntity.getPriority(),
                        new Money(priceEntity.getPrice(), priceEntity.getCurrency())
                ));
    }
}
