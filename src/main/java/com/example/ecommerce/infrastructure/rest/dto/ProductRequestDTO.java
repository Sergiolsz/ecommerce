package com.example.ecommerce.infrastructure.rest.dto;

import static com.example.ecommerce.common.utils.MessageCodes.PRODUCT_BRAND_POSITIVE;
import static com.example.ecommerce.common.utils.MessageCodes.PRODUCT_BRAND_REQUIRED;
import static com.example.ecommerce.common.utils.MessageCodes.PRODUCT_CURRENCY_REQUIRED;
import static com.example.ecommerce.common.utils.MessageCodes.PRODUCT_DATE_REQUIRED;
import static com.example.ecommerce.common.utils.MessageCodes.PRODUCT_ID_POSITIVE;
import static com.example.ecommerce.common.utils.MessageCodes.PRODUCT_PRICE_LIST_POSITIVE;
import static com.example.ecommerce.common.utils.MessageCodes.PRODUCT_PRICE_LIST_REQUIRED;
import static com.example.ecommerce.common.utils.MessageCodes.PRODUCT_PRICE_POSITIVE;
import static com.example.ecommerce.common.utils.MessageCodes.PRODUCT_PRICE_REQUIRED;
import static com.example.ecommerce.common.utils.MessageCodes.PRODUCT_PRIORITY_POSITIVE;
import static com.example.ecommerce.common.utils.MessageCodes.PRODUCT_PRIORITY_REQUIRED;
import static com.example.ecommerce.common.utils.MessageCodes.PRODUCT_PRODUCT_ID_REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public record ProductRequestDTO(
        @Schema(description = "Product ID", type = "integer", example = "1001") @NotNull(message = PRODUCT_PRODUCT_ID_REQUIRED) @Min(value = 1, message = PRODUCT_ID_POSITIVE) Integer productId,
        @Schema(description = "Brand ID", type = "integer", example = "1") @NotNull(message = PRODUCT_BRAND_REQUIRED) @Min(value = 1, message = PRODUCT_BRAND_POSITIVE) Integer brandId,
        @Schema(description = "Price list ID", type = "integer", example = "2") @NotNull(message = PRODUCT_PRICE_LIST_REQUIRED) @Min(value = 1, message = PRODUCT_PRICE_LIST_POSITIVE) Integer priceList,
        @Schema(description = "Product price", type = "number", format = "double", example = "99.99") @NotNull(message = PRODUCT_PRICE_REQUIRED) @DecimalMin(value = "0.0", inclusive = false, message = PRODUCT_PRICE_POSITIVE) BigDecimal price,
        @Schema(description = "Currency of the price", type = "string", example = "EUR") @NotBlank(message = PRODUCT_CURRENCY_REQUIRED) String currency,
        @DateTimeFormat(iso = ISO.DATE_TIME) @Schema(description = "Start date of validity", type = "string", format = "date-time", example = "2025-08-27T00:00:00") @NotNull(message = PRODUCT_DATE_REQUIRED) LocalDateTime startDate,
        @DateTimeFormat(iso = ISO.DATE_TIME) @Schema(description = "End date of validity", type = "string", format = "date-time", example = "2025-12-31T23:59:59") @NotNull(message = PRODUCT_DATE_REQUIRED) LocalDateTime endDate,
        @Schema(description = "Product priority", type = "integer", example = "1") @NotNull(message = PRODUCT_PRIORITY_REQUIRED) @Min(value = 1, message = PRODUCT_PRIORITY_POSITIVE) Integer priority) {

}
