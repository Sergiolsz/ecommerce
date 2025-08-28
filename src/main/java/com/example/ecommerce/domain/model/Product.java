package com.example.ecommerce.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Product(Integer productId, Integer brandId, Integer priceList, BigDecimal price,
                      String currency, LocalDateTime startDate, LocalDateTime endDate,
                      Integer priority) {

}