package com.example.test.mapper.impl;

import com.example.test.dto.product.ProductResponse;
import com.example.test.entity.Product;
import com.example.test.mapper.ProductMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapperImpl implements ProductMapper {
    @Override
    public List<ProductResponse> toDtoS(List<Product> all) {
        List<ProductResponse> productResponses = new ArrayList<>();
        for(Product product : all){
            productResponses.add(toDto(product));
        }
        return productResponses;
    }

    @Override
    public ProductResponse toDto(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setCategory(product.getCategory().getName());
        productResponse.setPrice(product.getPrice());
        return productResponse;
    }
}