package com.example.ecommerce.application.handler.query;

import com.example.ecommerce.infrastructure.mapper.ProductResponseMapper;
import com.example.ecommerce.common.i18n.MessageHandler;
import com.example.ecommerce.domain.exception.ProductNotFoundException;
import com.example.ecommerce.domain.model.Product;
import com.example.ecommerce.domain.repository.ProductQueryRepository;
import com.example.ecommerce.infrastructure.rest.dto.ProductResponseDTO;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetProductByProductIdHandler {

    private final ProductQueryRepository productQueryRepository;
    private final ProductResponseMapper productResponseMapper;
    private final MessageHandler messageHandler;

    /**
     * Retrieves products by the given productId.
     *
     * @param productId The unique identifier of the product. Must not be null.
     * @param locale    The locale for internationalization of error messages. If null,
     *                  defaults to system locale.
     * @return A list of ProductResponseDTO objects representing the retrieved products.
     * @throws ProductNotFoundException if no products are found with the given ID.
     */
    public List<ProductResponseDTO> handleGetProductsByProductId(Integer productId, Locale locale) {
        log.info("Request received to retrieve products by productId={}, locale={}", productId, locale);

        List<Product> products = productQueryRepository.getProductsByProductId(productId)
                .orElseThrow(() -> {
                    log.warn("No products found for productId={}", productId);
                    return new ProductNotFoundException(messageHandler, productId, locale);
                });

        List<ProductResponseDTO> responses = products.stream()
                .map(productResponseMapper::productToResponse)
                .toList();

        log.info("Returning {} products for productId={}", responses.size(), productId);
        return responses;
    }
}
