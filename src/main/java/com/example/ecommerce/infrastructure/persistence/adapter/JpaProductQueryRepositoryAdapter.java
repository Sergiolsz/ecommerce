package com.example.ecommerce.infrastructure.persistence.adapter;

import com.example.ecommerce.domain.model.Product;
import com.example.ecommerce.domain.repository.ProductQueryRepository;
import com.example.ecommerce.infrastructure.mapper.ProductEntityMapper;
import com.example.ecommerce.infrastructure.persistence.repository.JpaProductRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaProductQueryRepositoryAdapter implements ProductQueryRepository {

    private final JpaProductRepository productRepository;
    private final ProductEntityMapper productEntityMapper;


    @Override
    public Optional<List<Product>> getProductsByProductId(Integer productId) {

        return Optional.of(productRepository.findByProductId(productId).stream()
                .map(productEntityMapper::entityToProduct)
                .toList());
    }

    @Override
    public Optional<List<Product>> getProducts() {

        return Optional.of(productRepository.findAll().stream()
                .map(productEntityMapper::entityToProduct)
                .toList());
    }

    @Override
    public Optional<Product> findProductApplicablePrice(LocalDateTime applicationDate,
            Integer productId,
            Integer brandId) {

        return productRepository.findApplicableProducts(applicationDate, productId, brandId)
                .stream()
                .findFirst()
                .map(productEntityMapper::entityToProduct);
    }

    @Override
    public boolean existsProduct(Integer productId, Integer brandId, Integer priceList,
            BigDecimal price, String currency, LocalDateTime startDate, LocalDateTime endDate,
            Integer priority) {
        return productRepository.existsByProductIdAndBrandIdAndPriceListAndPriceAndCurrencyAndStartDateAndEndDateAndPriority(
                productId, brandId, priceList, price, currency, startDate, endDate, priority);
    }
}
