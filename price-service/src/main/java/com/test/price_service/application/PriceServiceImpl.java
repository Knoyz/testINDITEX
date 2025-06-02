package com.test.price_service.application;

import com.test.price_service.domain.ports.in.PriceServicePort;
import com.test.price_service.domain.ports.out.PriceEventProducerPort;
import com.test.price_service.domain.ports.out.PriceRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PriceServiceImpl implements PriceServicePort {

    private final PriceRepositoryPort priceRepository;
    private final PriceEventProducerPort priceEventProducerPort;

    @Override
    public Long getApplicablePrice(Long productId, Long brandId, String date) {
        return 0L;
    }
}
