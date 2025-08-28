package com.example.ecommerce.domain.repository;

import com.example.ecommerce.domain.model.Product;

public interface ProductCommandRepository {

    Boolean saveProductIfNotExists(Product product);
}
