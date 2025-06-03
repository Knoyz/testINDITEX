package com.test.price_service.infrastructure.adapters.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.price_service.application.PriceServiceImpl;
import com.test.price_service.application.dtos.PriceRequest;
import com.test.price_service.domain.model.DateRange;
import com.test.price_service.domain.model.Money;
import com.test.price_service.domain.model.Price;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RequiredArgsConstructor
@AutoConfigureMockMvc
public class PriceControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    @ExtendWith(MockitoExtension.class)
    private PriceServiceImpl priceServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

    private final UUID[] priceIds = {
            UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
            UUID.fromString("6ba7b810-9dad-11d1-80b4-00c04fd430c8"),
            UUID.fromString("7d793037-a076-4c71-8a6c-86e3945d4e1a"),
            UUID.fromString("8f14e45f-ceea-4f0c-9d1c-7c1f3e4b7f2b")
    };

    @Test
    @DisplayName("Test 1: Check price on 2020-06-14 10:00 for product 35455, brand 1")
    void testGetPriceOnJune14At10() throws Exception {
        PriceRequest request = new PriceRequest(35455L, 1L, "2020-06-14-10.00.00");

        Price price = new Price(
                priceIds[0],
                1L,
                new DateRange(
                        LocalDateTime.parse("2020-06-14T00:00:00"),
                        LocalDateTime.parse("2020-12-31T23:59:59")
                ),
                1,
                35455L,
                0,
                new Money(new BigDecimal("35.50"), "EUR")
        );

        when(priceServiceImpl.getApplicablePrice(35455L, 1L, "2020-06-14-10.00.00")).thenReturn(price);

        mockMvc.perform(get("/api/v1/prices/current")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.currency").value("EUR"));

    }


    @Test
    @DisplayName("Test 2: Check price on 2020-06-14 16:00 for product 35455, brand 1")
    void testGetPriceOnJune14At16() throws Exception {
        PriceRequest request = new PriceRequest(35455L, 1L, "2020-06-14-16.00.00");

        Price price = new Price(
                priceIds[1],
                1L,
                new DateRange(
                        LocalDateTime.parse("2020-06-14T15:00:00"),
                        LocalDateTime.parse("2020-06-14T18:30:00")
                ),
                2,
                35455L,
                1,
                new Money(new BigDecimal("25.45"), "EUR")
        );

        when(priceServiceImpl.getApplicablePrice(35455L, 1L, "2020-06-14-16.00.00")).thenReturn(price);

        mockMvc.perform(get("/api/v1/prices/current")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T15:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-06-14T18:30:00"))
                .andExpect(jsonPath("$.price").value(25.45))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    @DisplayName("Test 3: Check price on 2020-06-14 21:00 for product 35455, brand 1")
    void testGetPriceOnJune14At21() throws Exception {
        PriceRequest request = new PriceRequest(35455L, 1L, "2020-06-14-21.00.00");

        Price price = new Price(
                priceIds[0],
                1L,
                new DateRange(
                        LocalDateTime.parse("2020-06-14T00:00:00"),
                        LocalDateTime.parse("2020-12-31T23:59:59")
                ),
                1,
                35455L,
                0,
                new Money(new BigDecimal("35.50"), "EUR")
        );

        when(priceServiceImpl.getApplicablePrice(35455L, 1L, "2020-06-14-21.00.00")).thenReturn(price);

        mockMvc.perform(get("/api/v1/prices/current")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    @DisplayName("Test 4: Check price on 2020-06-15 10:00 for product 35455, brand 1")
    void testGetPriceOnJune15At10() throws Exception {
        PriceRequest request = new PriceRequest(35455L, 1L, "2020-06-15-10.00.00");

        Price price = new Price(
                priceIds[2],
                1L,
                new DateRange(
                        LocalDateTime.parse("2020-06-15T00:00:00"),
                        LocalDateTime.parse("2020-06-15T11:00:00")
                ),
                3,
                35455L,
                1,
                new Money(new BigDecimal("30.50"), "EUR")
        );

        when(priceServiceImpl.getApplicablePrice(35455L, 1L, "2020-06-15-10.00.00")).thenReturn(price);

        mockMvc.perform(get("/api/v1/prices/current")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(3))
                .andExpect(jsonPath("$.startDate").value("2020-06-15T00:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-06-15T11:00:00"))
                .andExpect(jsonPath("$.price").value(30.50))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    @DisplayName("Test 5: Check price on 2020-06-16 21:00 for product 35455, brand 1")
    void testGetPriceOnJune16At21() throws Exception {
        PriceRequest request = new PriceRequest(35455L, 1L, "2020-06-16-21.00.00");

        Price price = new Price(
                priceIds[3],
                1L,
                new DateRange(
                        LocalDateTime.parse("2020-06-15T16:00:00"),
                        LocalDateTime.parse("2020-12-31T23:59:59")
                ),
                4,
                35455L,
                1,
                new Money(new BigDecimal("38.95"), "EUR")
        );

        when(priceServiceImpl.getApplicablePrice(35455L, 1L, "2020-06-16-21.00.00")).thenReturn(price);

        mockMvc.perform(get("/api/v1/prices/current")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(4))
                .andExpect(jsonPath("$.startDate").value("2020-06-15T16:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
                .andExpect(jsonPath("$.price").value(38.95))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }


}
