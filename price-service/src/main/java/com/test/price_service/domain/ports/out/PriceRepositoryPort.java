package com.test.price_service.domain.ports.out;

import com.test.price_service.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PriceRepositoryPort {

    List<Price> findApplicablePrice(Long productId, Long brandId, LocalDateTime date);
}
