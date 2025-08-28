package com.example.ecommerce.infrastructure.kafka.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductCreatedEvent(
        Integer productId,
        Integer brandId,
        Integer priceList,
        BigDecimal price,
        String currency,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Integer priority) {

}
