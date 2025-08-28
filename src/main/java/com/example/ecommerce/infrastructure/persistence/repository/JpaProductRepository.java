package com.example.ecommerce.infrastructure.persistence.repository;

import com.example.ecommerce.infrastructure.persistence.entity.ProductEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {

    boolean existsByProductIdAndBrandIdAndPriceListAndPriceAndCurrencyAndStartDateAndEndDateAndPriority(
            Integer productId,
            Integer brandId,
            Integer priceList,
            BigDecimal price,
            String currency,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Integer priority
    );


    List<ProductEntity> findByProductId(Integer productId);

    @Query("""
            SELECT p FROM ProductEntity p
            WHERE p.productId = :productId
              AND p.brandId = :brandId
              AND :applicationDate BETWEEN p.startDate AND p.endDate
            ORDER BY p.priority DESC
            """)
    List<ProductEntity> findApplicableProducts(
            @Param("applicationDate") LocalDateTime applicationDate,
            @Param("productId") Integer productId,
            @Param("brandId") Integer brandId);
}