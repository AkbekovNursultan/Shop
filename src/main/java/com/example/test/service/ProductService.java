package com.example.test.service;

import com.example.test.dto.product.ProductAddRequest;

import java.util.List;

public interface ProductService {
    void addProduct(ProductAddRequest request, String token);
}