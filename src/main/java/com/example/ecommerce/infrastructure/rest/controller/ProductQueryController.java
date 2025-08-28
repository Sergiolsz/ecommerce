package com.example.ecommerce.infrastructure.rest.controller;

import com.example.ecommerce.application.handler.query.GetAllProductsHandler;
import com.example.ecommerce.application.handler.query.GetProductApplicablePriceHandler;
import com.example.ecommerce.application.handler.query.GetProductByProductIdHandler;
import com.example.ecommerce.infrastructure.rest.dto.ProductResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Endpoints para consultar productos y precios aplicables")
@Validated
public class ProductQueryController {

    private final GetProductByProductIdHandler getProductByProductIdHandler;
    private final GetProductApplicablePriceHandler getProductApplicablePriceHandler;
    private final GetAllProductsHandler getAllProductsHandler;

    @GetMapping("/{productId}")
    @Operation(
            summary = "Obtener productos por ID",
            description = "Devuelve una lista de productos que coinciden con el ID proporcionado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de productos encontrada",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductResponseDTO.class)))),
                    @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
            }
    )
    public ResponseEntity<List<ProductResponseDTO>> getProductByProductId(
            @Parameter(description = "ID del producto", required = true, example = "35455")
            @PathVariable Integer productId,

            @Parameter(description = "Locale para la internacionalización", required = false)
            Locale locale) {
        return ResponseEntity.ok(
                getProductByProductIdHandler.handleGetProductsByProductId(productId, locale));
    }

    @GetMapping
    @Operation(
            summary = "Obtener todos los productos",
            description = "Devuelve la lista completa de productos disponibles",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista completa de productos",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductResponseDTO.class))))
            }
    )
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(
            @Parameter(description = "Locale para la internacionalización", required = false)
            Locale locale) {
        return ResponseEntity.ok(getAllProductsHandler.handleGetAllProducts(locale));
    }

    @GetMapping("/price")
    @Operation(
            summary = "Obtener precio aplicable de un producto",
            description = "Devuelve el precio que se aplica a un producto específico en una fecha determinada y para una marca concreta",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Precio aplicable encontrado",
                            content = @Content(schema = @Schema(implementation = ProductResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Producto o precio no encontrado", content = @Content)
            }
    )
    public ResponseEntity<ProductResponseDTO> getApplicablePrice(
            @Parameter(description = "Fecha y hora de aplicación del precio",
                    required = true,
                    example = "2025-08-28T15:30:00",
                    schema = @Schema(type = "string", format = "date-time"))
            @RequestParam("applicationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,

            @Parameter(description = "ID del producto", required = true, example = "35455")
            @RequestParam("productId") Integer productId,

            @Parameter(description = "ID de la marca", required = true, example = "1")
            @RequestParam("brandId") Integer brandId,

            @Parameter(description = "Locale para la internacionalización")
            Locale locale) {

        return ResponseEntity.ok(
                getProductApplicablePriceHandler.handleGetProductApplicablePrice(applicationDate, productId, brandId, locale));
    }
}