package com.example.ecommerce.infrastructure.mapper;

import com.example.ecommerce.common.mapper.TimeMapper;
import com.example.ecommerce.domain.model.Product;
import com.example.ecommerce.infrastructure.avro.ProductAvro;
import com.example.ecommerce.infrastructure.rest.dto.ProductRequestDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TimeMapper.class)
public interface ProductAvroMapper {

    @Mapping(target = "price", expression = "java(productRequestDTO.price().doubleValue())")
    @Mapping(target = "startDate", expression = "java(productRequestDTO.startDate().toString())")
    @Mapping(target = "endDate", expression = "java(productRequestDTO.endDate().toString())")
    ProductAvro productDtoToAvro(ProductRequestDTO productRequestDTO);

    @InheritInverseConfiguration
    ProductRequestDTO avroToProductDto(ProductAvro avro);

    @Mapping(target = "price", expression = "java(BigDecimal.valueOf(productAvro.getPrice()))")
    @Mapping(target = "startDate", expression = "java(LocalDateTime.parse(productAvro.getStartDate()))")
    @Mapping(target = "endDate", expression = "java(LocalDateTime.parse(productAvro.getEndDate()))")
    Product avroToProduct(ProductAvro productAvro);
}