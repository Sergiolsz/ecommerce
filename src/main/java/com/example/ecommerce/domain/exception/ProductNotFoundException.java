package com.example.ecommerce.domain.exception;

import com.example.ecommerce.common.i18n.MessageHandler;
import com.example.ecommerce.common.utils.MessageCodes;
import java.time.LocalDateTime;
import java.util.Locale;
import lombok.Getter;

@Getter
public class ProductNotFoundException extends DomainException {

    private Integer productId;
    private Integer brandId;
    private LocalDateTime applicationDate;

    /**
     * Constructs a new ProductNotFoundException with a localized message.
     *
     * @param messageHandler The message handler for localization.
     * @param locale         The locale for the message.
     */
    public ProductNotFoundException(MessageHandler messageHandler, Locale locale) {
        super(messageHandler.getMessage(
                MessageCodes.PRODUCTS_NOT_FOUND, locale));
    }

    /**
     * Constructs a new ProductNotFoundException with a localized message.
     *
     * @param messageHandler The message handler for localization.
     * @param productId      The ID of the product.
     * @param locale         The locale for the message.
     */
    public ProductNotFoundException(MessageHandler messageHandler, Integer productId,
            Locale locale) {
        super(messageHandler.getMessage(
                MessageCodes.PRODUCT_NOT_FOUND, productId, locale));
    }

    /**
     * Constructs a new ProductNotFoundException with a localized message.
     *
     * @param productId       The ID of the product.
     * @param brandId         The ID of the brand.
     * @param applicationDate The application date used in the search.
     * @param messageHandler  The message handler for localization.
     * @param locale          The locale for the message.
     */
    public ProductNotFoundException(Integer productId, Integer brandId,
            LocalDateTime applicationDate,
            MessageHandler messageHandler, Locale locale) {
        super(messageHandler.getMessage(
                MessageCodes.PRODUCTS_PRICE_NOT_FOUND, locale, productId, brandId,
                applicationDate));
        this.productId = productId;
        this.brandId = brandId;
        this.applicationDate = applicationDate;
    }

}