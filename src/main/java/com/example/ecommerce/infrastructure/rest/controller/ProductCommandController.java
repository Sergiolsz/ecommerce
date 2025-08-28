package com.example.ecommerce.infrastructure.rest.controller;

import com.example.ecommerce.application.handler.command.CreateProductHandler;
import com.example.ecommerce.infrastructure.rest.dto.ProductRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductCommandController {

    private final CreateProductHandler createProductHandler;

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        createProductHandler.createProduct(productRequestDTO);

        return ResponseEntity.ok("Product event sent to Kafka");
    }
}