package com.example.ecommerce.infrastructure.persistence.adapter;

import com.example.ecommerce.domain.model.Product;
import com.example.ecommerce.domain.repository.ProductCommandRepository;
import com.example.ecommerce.infrastructure.mapper.ProductEntityMapper;
import com.example.ecommerce.infrastructure.persistence.entity.ProductEntity;
import com.example.ecommerce.infrastructure.persistence.repository.JpaProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaProductCommandRepositoryAdapter implements ProductCommandRepository {

    private final JpaProductRepository productRepository;
    private final ProductEntityMapper productEntityMapper;

    /**
     * Guarda un producto solo si no existe ya en la base de datos.
     * @param product Producto a guardar
     * @return true si se guardó, false si era duplicado
     */
    @Transactional
    @Override
    public Boolean saveProductIfNotExists(Product product) {
        boolean exists = productRepository.existsByProductIdAndBrandIdAndPriceListAndPriceAndCurrencyAndStartDateAndEndDateAndPriority(
                product.productId(),
                product.brandId(),
                product.priceList(),
                product.price(),
                product.currency(),
                product.startDate(),
                product.endDate(),
                product.priority()
        );

        if (!exists) {
            ProductEntity entity = productEntityMapper.productToEntity(product);
            productRepository.save(entity);
            return true;
        }

        return false;
    }
}