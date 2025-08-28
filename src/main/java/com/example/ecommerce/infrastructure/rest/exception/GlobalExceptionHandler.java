package com.example.ecommerce.infrastructure.rest.exception;

import static com.example.ecommerce.common.utils.MessageCodes.CODE_INTERNAL_ERROR;
import static com.example.ecommerce.common.utils.MessageCodes.CODE_VALIDATION_ERROR;
import static com.example.ecommerce.common.utils.MessageCodes.PRODUCT_DATE_INVALID;
import static com.example.ecommerce.common.utils.MessageCodes.PRODUCT_NOT_FOUND;
import static com.example.ecommerce.common.utils.MessageCodes.PRODUCT_NOT_FOUND_CODE;
import static com.example.ecommerce.common.utils.MessageCodes.TYPE_INTERNAL_ERROR;

import com.example.ecommerce.common.i18n.MessageHandler;
import com.example.ecommerce.domain.exception.ProductNotFoundException;
import com.example.ecommerce.infrastructure.rest.dto.ErrorResponse;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageHandler messageHandler;
    private final Locale defaultLocale = Locale.getDefault();

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(ProductNotFoundException ex) {

        String message = messageHandler.getMessage(PRODUCT_NOT_FOUND, ex.getProductId());
        String code = messageHandler.getMessage(PRODUCT_NOT_FOUND_CODE);

        ErrorResponse errorResponse = new ErrorResponse(code, message,
                HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName =
                    (error instanceof FieldError fe) ? fe.getField() : error.getObjectName();
            String message = messageHandler.getMessage(error.getDefaultMessage());
            errors.put(fieldName, message);
        });

        String code = messageHandler.getMessage(CODE_VALIDATION_ERROR);
        String message = errors.toString(); // Puedes convertirlo a JSON o dejarlo como String simple

        ErrorResponse errorResponse = new ErrorResponse(code, message,
                HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {

        String message = messageHandler.getMessage(TYPE_INTERNAL_ERROR);
        String code = messageHandler.getMessage(CODE_INTERNAL_ERROR);

        ErrorResponse errorResponse = new ErrorResponse(code, message,
                HttpStatus.INTERNAL_SERVER_ERROR.value());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex,
            Locale locale) {

        String message;
        if (ex.getRequiredType() != null && ex.getRequiredType()
                .equals(java.time.LocalDateTime.class)) {
            message = messageHandler.getMessage(PRODUCT_DATE_INVALID, null, locale);
        } else {
            message = ex.getMessage();
        }

        ErrorResponse errorResponse = new ErrorResponse("TYPE_MISMATCH", message,
                HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.badRequest().body(errorResponse);
    }
}