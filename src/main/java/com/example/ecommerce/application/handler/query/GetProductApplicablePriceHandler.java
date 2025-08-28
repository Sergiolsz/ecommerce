package com.example.ecommerce.application.handler.query;

import com.example.ecommerce.infrastructure.mapper.ProductResponseMapper;
import com.example.ecommerce.common.i18n.MessageHandler;
import com.example.ecommerce.domain.exception.ProductNotFoundException;
import com.example.ecommerce.domain.model.Product;
import com.example.ecommerce.domain.repository.ProductQueryRepository;
import com.example.ecommerce.infrastructure.rest.dto.ProductResponseDTO;
import java.time.LocalDateTime;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetProductApplicablePriceHandler {

    private final ProductQueryRepository productQueryRepository;
    private final ProductResponseMapper productResponseMapper;
    private final MessageHandler messageHandler;

    /**
     * Retrieves the applicable product price for a given date, productId and brandId.
     *
     * @param applicationDate The date and time for which the product price is to be retrieved. This
     *                        is used to determine which price is applicable based on the product's
     *                        price list validity period. Must not be null.
     * @param productId       The unique identifier of the product. Must not be null.
     * @param brandId         The unique identifier of the brand associated with the product. Must
     *                        not be null.
     * @param locale          The locale for internationalization of error messages. If null,
     *                        defaults to system locale.
     * @return A ProductResponseDTO object representing the retrieved product.
     * @throws ProductNotFoundException if no product is found with the given ID.
     */
    public ProductResponseDTO handleGetProductApplicablePrice(LocalDateTime applicationDate,
            Integer productId, Integer brandId, Locale locale) {
        log.info("Request received to retrieve applicable price. productId={}, brandId={}, date={}, locale={}",
                productId, brandId, applicationDate, locale);

        Product product = productQueryRepository.findProductApplicablePrice(applicationDate, productId, brandId)
                .orElseThrow(() -> {
                    log.warn("No product found for productId={}, brandId={}, date={}",
                            productId, brandId, applicationDate);
                    return new ProductNotFoundException(productId, brandId, applicationDate, messageHandler, locale);
                });

        ProductResponseDTO response = productResponseMapper.productToResponse(product);

        log.info("Returning applicable price for productId={}, brandId={}, priceList={}",
                productId, brandId, response.priceList());
        return response;
    }
}
