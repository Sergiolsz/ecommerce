package com.example.ecommerce.infrastructure.mapper;

import com.example.ecommerce.domain.model.Product;
import com.example.ecommerce.infrastructure.config.MapStructConfig;
import com.example.ecommerce.infrastructure.persistence.entity.ProductEntity;
import java.util.List;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", config = MapStructConfig.class)
public interface ProductEntityMapper {

    Product entityToProduct(ProductEntity entity);

    List<Product> entitiesToProducts(List<ProductEntity> entities);

    @Mapping(target = "id", ignore = true)
    ProductEntity productToEntity(Product domain);

    @Named("uuidToString")
    default String uuidToString(UUID id) {
        return id != null ? id.toString() : null;
    }

    @Named("stringToUuid")
    default UUID stringToUuid(String id) {
        return id != null && !id.isBlank() ? UUID.fromString(id) : null;
    }
}