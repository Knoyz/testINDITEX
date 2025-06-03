package com.test.price_service.infrastructure.adapters.out;

import com.test.price_service.domain.model.DateRange;
import com.test.price_service.domain.model.Money;
import com.test.price_service.domain.model.Price;
import com.test.price_service.domain.ports.out.PriceRepositoryPort;
import com.test.price_service.infrastructure.entity.PriceEntity;
import com.test.price_service.infrastructure.repository.PriceH2Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class PriceRepositoryAdapter implements PriceRepositoryPort {

    private final PriceH2Repository priceH2Repository;

    @Override
    public List<Price> findApplicablePrice(Long productId, Long brandId, LocalDateTime date) {
        return priceH2Repository.findByProductIdAndBrandId(productId, brandId)
                .stream().map(priceEntity -> new Price(
                        priceEntity.getPriceId(),
                        priceEntity.getBrandId(),
                        new DateRange(priceEntity.getStartDate(), priceEntity.getEndDate()),
                        priceEntity.getPriceList(),
                        priceEntity.getProductId(),
                        priceEntity.getPriority(),
                        new Money(priceEntity.getPrice(), priceEntity.getCurrency())
                )).collect(Collectors.toList());
    }
}
