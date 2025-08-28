package com.example.ecommerce.infrastructure.mapper;

import com.example.ecommerce.domain.model.Product;
import com.example.ecommerce.infrastructure.rest.dto.ProductResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductResponseMapper {

    ProductResponseDTO productToResponse(Product product);
}
