package com.test.price_service.infrastructure.repository;

import com.test.price_service.domain.model.Price;
import com.test.price_service.infrastructure.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceH2Repository extends JpaRepository<PriceEntity, Long> {

    List<PriceEntity> findByProductIdAndBrandId(Long productId, Long brandId);

}
