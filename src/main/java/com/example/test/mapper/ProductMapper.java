package com.example.test.mapper;

import com.example.test.dto.product.ProductResponse;
import com.example.test.entity.Product;

import java.util.List;

public interface ProductMapper {
    List<ProductResponse> toDtoS(List<Product> all);
    ProductResponse toDto(Product product);
}
