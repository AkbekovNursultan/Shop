package com.example.test.controller;

import com.example.test.dto.product.ProductRequest;
import com.example.test.dto.product.ProductResponse;
import com.example.test.entity.Product;
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

    @GetMapping("/{id}")
    public ProductResponse productResponse(@PathVariable Long id){
        return productService.getById(id);
    }
    @GetMapping("/all")
    public List<ProductResponse> getAll(){
        return productService.getAll();
    }
    @PostMapping("/order/{workerId}")
    public void register(@RequestBody ProductRequest productRequest, @PathVariable Long workerId){
        productService.register(productRequest,workerId);
    }
    @PostMapping("/deliver/{productId}/{delivererId}")
    public void deliver(@PathVariable Long productId, @PathVariable Long delivererId){
        productService.deliver(productId, delivererId);
    }
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        productService.deleteProduct(id);
    }
}
