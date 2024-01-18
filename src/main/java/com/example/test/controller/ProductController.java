package com.example.test.controller;

import com.example.test.dto.product.ProductAddRequest;
import com.example.test.dto.product.ProductResponse;
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
    public void register(@RequestBody ProductAddRequest productAddRequest, @RequestHeader("Authorization") String token) {
        productService.addProduct(productAddRequest, token);
    }
    @GetMapping("/category")
    public List<ProductResponse> bookResponses(@RequestParam String category){
        return productService.getAll(category);
    }
    @PostMapping("/buy")
    public void buy(@RequestParam String code, @RequestHeader("Authorization") String token){
        productService.buy(code, token);
    }
    @DeleteMapping("/remove/{id}")
    public void removeById(@PathVariable Long id, @RequestHeader("Authorization") String token){
        productService.deleteById(id, token);
    }
}
