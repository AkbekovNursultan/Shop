package com.example.test.service;

import com.example.test.dto.product.ProductAddRequest;
import com.example.test.dto.product.ProductResponse;

import java.util.List;

public interface ProductService {
    void addProduct(ProductAddRequest request, String token);

    List<ProductResponse> getAll(String category);

    void buy(String code, String token);

    void deleteById(Long id, String token);
}