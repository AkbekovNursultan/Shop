package com.example.test.service;

import com.example.test.dto.product.ProductRequest;
import com.example.test.dto.product.ProductResponse;
import com.example.test.entity.Product;

import java.util.List;

public interface ProductService {
    ProductResponse getById(Long id);

    List<ProductResponse> getAll();

    void register(ProductRequest productRequest, Long workerId);

    void deleteProduct(Long id);

    void deliver(Long productId, Long delivererId);
}
