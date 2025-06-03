package com.test.price_service.infrastructure.adapters.in.rest;

import com.test.price_service.application.PriceServiceImpl;
import com.test.price_service.application.dtos.PriceRequest;
import com.test.price_service.application.dtos.PriceResponse;
import com.test.price_service.domain.model.Price;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/prices")
public class PriceController {

    private final PriceServiceImpl priceServiceImpl;

    @GetMapping("/current")
    public ResponseEntity<PriceResponse> getPrice(@RequestBody @Validated PriceRequest priceRequest) {

        // In a real application, you would call the service layer to get the price.
        Price price = priceServiceImpl.getApplicablePrice(priceRequest.productId(), priceRequest.brandId(), priceRequest.applicationDate());

        // Convertirlo a PriceResponse para que no se filtre el modelo hacia el exterior
        PriceResponse priceResponse = new PriceResponse(
                price.productId(),
                price.brandId(),
                price.priceList(),
                price.dateRange().startDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                price.dateRange().endDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                price.price().amount(),
                price.price().currency());

        return ResponseEntity.ok(priceResponse);
    }
}