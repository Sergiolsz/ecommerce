package com.example.ecommerce.application.handler.query;

import com.example.ecommerce.infrastructure.mapper.ProductResponseMapper;
import com.example.ecommerce.common.i18n.MessageHandler;
import com.example.ecommerce.domain.exception.ProductNotFoundException;
import com.example.ecommerce.domain.model.Product;
import com.example.ecommerce.domain.repository.ProductQueryRepository;
import com.example.ecommerce.infrastructure.rest.dto.ProductResponseDTO;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAllProductsHandler {

    private final ProductQueryRepository productQueryRepository;
    private final ProductResponseMapper productResponseMapper;
    private final MessageHandler messageHandler;

    /**
     * Retrieves all products.
     *
     * @param locale The locale for internationalization of error messages. If null,
     *               defaults to system locale.
     * @return A list of ProductResponseDTO objects representing all products.
     * @throws ProductNotFoundException if no products are found.
     */
    public List<ProductResponseDTO> handleGetAllProducts(Locale locale) {

        log.info("Request received to retrieve all products. Locale={}", locale);

        List<Product> products = productQueryRepository.getProducts()
                .orElseThrow(() -> {
                    log.warn("No products found. Throwing ProductNotFoundException");
                    return new ProductNotFoundException(messageHandler, locale);
                });

        List<ProductResponseDTO> responses = products.stream()
                .map(productResponseMapper::productToResponse)
                .toList();

        log.info("Returning {} products", responses.size());
        return responses;
    }
}