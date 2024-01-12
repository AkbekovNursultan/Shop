package com.example.test.mapper;

import com.example.test.dto.product.ProductResponse;
import com.example.test.entity.Product;

import java.util.List;

public interface ProductMapper {
    ProductResponse toDto(Product product);

    List<ProductResponse> toDtoS(List<Product> all);


}
