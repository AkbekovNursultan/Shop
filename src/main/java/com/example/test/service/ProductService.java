package com.example.test.service;

import com.example.test.dto.product.ProductAddRequest;
import com.example.test.dto.product.ProductResponse;

import java.util.List;

public interface ProductService {
    String addNewProduct(ProductAddRequest request, String token);

    List<ProductResponse> getAll(String category);

    void buy(String code, Integer amount, String token);

    void restock(String code, Integer amount, String token);

    void delete(String code, String token);
}