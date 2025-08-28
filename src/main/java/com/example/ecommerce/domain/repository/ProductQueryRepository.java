package com.example.ecommerce.domain.repository;

import com.example.ecommerce.domain.model.Product;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductQueryRepository {

    Optional<List<Product>> getProductsByProductId(Integer productId);

    Optional<List<Product>> getProducts();

    Optional<Product> findProductApplicablePrice(LocalDateTime applicationDate, Integer productId,
            Integer brandId);

    boolean existsProduct(Integer productId, Integer brandId, Integer priceList, BigDecimal price,
            String currency, LocalDateTime startDate, LocalDateTime endDate, Integer priority);
}