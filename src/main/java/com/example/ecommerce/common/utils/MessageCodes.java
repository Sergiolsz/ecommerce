package com.example.ecommerce.common.utils;

/**
 * Class that contains all the message codes used in the project. Each constant corresponds to a key
 * in the messages.properties file.
 */
public final class MessageCodes {

    /**
     * Common keys for response structure.
     */
    public static final String CODE = "code";
    public static final String ERROR = "error";

    /**
     * Message codes for product-related operations.
     */
    public static final String PRODUCT_NOT_FOUND = "product.notFound";
    public static final String PRODUCTS_PRICE_NOT_FOUND = "product.price.notFound";
    public static final String PRODUCTS_NOT_FOUND = "product.list.notFound";
    public static final String PRODUCT_NOT_FOUND_CODE = "product.not.found.code";
    public static final String PRODUCT_ALREADY_EXISTS = "product.already.exists";

    /**
     * Validation message codes for product operations.
     */
    public static final String PRODUCT_PRODUCT_ID_REQUIRED = "valid.productId.required";
    public static final String PRODUCT_ID_POSITIVE = "valid.productId.positive";
    public static final String PRODUCT_BRAND_REQUIRED = "valid.brand.required";
    public static final String PRODUCT_BRAND_POSITIVE = "valid.brand.positive";
    public static final String PRODUCT_PRICE_LIST_REQUIRED = "valid.priceList.required";
    public static final String PRODUCT_PRICE_LIST_POSITIVE = "valid.priceList.positive";
    public static final String PRODUCT_PRICE_REQUIRED = "valid.price.required";
    public static final String PRODUCT_PRICE_POSITIVE = "valid.price.positive";
    public static final String PRODUCT_CURRENCY_REQUIRED = "valid.currency.required";
    public static final String PRODUCT_DATE_INVALID = "valid.date.invalid";
    public static final String PRODUCT_DATE_REQUIRED = "valid.date.required";
    public static final String PRODUCT_PRIORITY_REQUIRED = "valid.priority.required";
    public static final String PRODUCT_PRIORITY_POSITIVE = "valid.priority.positive";

    /**
     * Kafka topic names for product events.
     */
    public static final String TOPIC_PRODUCT_CREATED = "products-created";
    public static final String GROUP_ID_ECOMMERCE_SERVICE = "ecommerce-service";
    public static final String KAFKA_PRODUCT_ERROR = "kafka.topic.product.error";

    /**
     * General error types.
     */
    public static final String TYPE_INTERNAL_ERROR = "type.internal.error";

    /**
     * General error codes.
     */
    public static final String CODE_NOT_FOUND = "code.notFound";
    public static final String CODE_ALREADY_EXISTS = "code.already.exists";
    public static final String CODE_VALIDATION_ERROR = "code.validation.error";
    public static final String CODE_INTERNAL_ERROR = "code.internal.error";


    private MessageCodes() {
    }

}