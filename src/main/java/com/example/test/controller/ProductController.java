package com.example.test.controller;

import com.example.test.dto.product.ProductAddRequest;
import com.example.test.repository.ProductRepository;
import com.example.test.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductService productService;

    @PostMapping("/add")
    public void register(@RequestBody ProductAddRequest productAddRequest, @RequestHeader String token) {
        productService.addProduct(productAddRequest, token);
    }
}
