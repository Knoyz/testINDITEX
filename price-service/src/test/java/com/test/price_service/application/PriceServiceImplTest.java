package com.test.price_service.application;

import com.test.price_service.domain.model.DateRange;
import com.test.price_service.domain.model.Money;
import com.test.price_service.domain.model.Price;
import com.test.price_service.infrastructure.adapters.in.rest.exception.PriceValidationException;
import com.test.price_service.infrastructure.adapters.out.PriceProducerAdapter;
import com.test.price_service.infrastructure.adapters.out.PriceRepositoryAdapter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class PriceServiceImplTest {

    @Mock
    private PriceRepositoryAdapter priceRepositoryAdapter;

    @Mock
    private PriceProducerAdapter priceProducerAdapter;

    @InjectMocks
    private PriceServiceImpl priceServiceImpl;

    private final UUID[] priceIds = {UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), UUID.fromString("6ba7b810-9dad-11d1-80b4-00c04fd430c8"), UUID.fromString("7d793037-a076-4c71-8a6c-86e3945d4e1a"), UUID.fromString("8f14e45f-ceea-4f0c-9d1c-7c1f3e4b7f2b")};

    private Price price1 = new Price(priceIds[0], 1L, new DateRange(LocalDateTime.parse("2020-06-14T00:00:00"), LocalDateTime.parse("2020-12-31T23:59:59")), 1, 35455L, 0, new Money(new BigDecimal("35.50"), "EUR"));

    private Price price2 = new Price(priceIds[1], 1L, new DateRange(LocalDateTime.parse("2020-06-14T15:00:00"), LocalDateTime.parse("2020-06-14T18:30:00")), 2, 35455L, 1, new Money(new BigDecimal("25.45"), "EUR"));

    private Price price3 = new Price(priceIds[0], 1L, new DateRange(LocalDateTime.parse("2020-06-14T00:00:00"), LocalDateTime.parse("2020-12-31T23:59:59")), 1, 35455L, 0, new Money(new BigDecimal("35.50"), "EUR"));

    private Price price4 = new Price(priceIds[2], 1L, new DateRange(LocalDateTime.parse("2020-06-15T00:00:00"), LocalDateTime.parse("2020-06-15T11:00:00")), 3, 35455L, 1, new Money(new BigDecimal("30.50"), "EUR"));

    private List<Price> priceList = List.of(price1, price2, price3, price4);

    @Test
    void getCorrectApplicablePriceFor2020_06_14_10_00_00() {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");

        when(priceRepositoryAdapter.findApplicablePrice(35455L, 1L, LocalDateTime.parse("2020-06-14-10.00.00", dateTimeFormatter))).thenReturn(
                priceList.stream()
                        .filter(price -> price.productId().equals(35455L) && price.brandId().equals(1L))
                        .collect(Collectors.toList()));

        Price returnedPrice = priceServiceImpl.getApplicablePrice(35455L, 1L, "2020-06-14-10.00.00");

        assertNotNull(returnedPrice);
        assertEquals(price1.brandId(), returnedPrice.brandId());
        assertEquals(price1.dateRange().startDate(), returnedPrice.dateRange().startDate());
        assertEquals(price1.dateRange().endDate(), returnedPrice.dateRange().endDate());
        assertEquals(price1.priceList(), returnedPrice.priceList());
        assertEquals(price1.productId(), returnedPrice.productId());
        assertEquals(price1.priority(), returnedPrice.priority());
        assertEquals(price1.price().amount(), returnedPrice.price().amount());
        assertEquals(price1.price().currency(), returnedPrice.price().currency());

    }

    @Test
    void getPriceNotFound() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");

        when(priceRepositoryAdapter.findApplicablePrice(99999L, 1L, LocalDateTime.parse("2020-06-14-10.00.00", dateTimeFormatter))).thenReturn(
                List.of());

        assertThrows(PriceValidationException.class, () -> {
            priceServiceImpl.getApplicablePrice(99999L, 1L, "2020-06-14-10.00.00");
        });
    }


}