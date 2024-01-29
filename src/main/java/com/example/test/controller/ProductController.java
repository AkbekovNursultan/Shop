package com.example.test.controller;

import com.example.test.dto.product.ProductAddRequest;
import com.example.test.dto.product.ProductResponse;
import com.example.test.enums.Role;
import com.example.test.exception.BlockedException;
import com.example.test.repository.ProductRepository;
import com.example.test.service.AuthService;
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
    private final AuthService authService;

    @PostMapping("/add_new_product")
    public String addNewProduct(@RequestBody ProductAddRequest productAddRequest, @RequestHeader("Authorization") String token) {
        return productService.addNewProduct(productAddRequest, token);
    }
    @PostMapping("/restock")
    public void restock(@RequestParam String code, @RequestParam Integer amount, @RequestHeader("Authorization") String token){
        productService.restock(code, amount, token);
    }
    @GetMapping("/category")
    public List<ProductResponse> bookResponses(@RequestParam String category){
        return productService.getAll(category);
    }
    @PostMapping("/buy")
    public void buy(@RequestParam String code, @RequestParam Integer amount, @RequestHeader("Authorization") String token){
        productService.buy(code, amount, token);
    }
    @DeleteMapping("/delete")
    public String delete(@RequestParam String code, @RequestHeader("Authorization") String token){
        if(!authService.getUserFromToken(token).getRole().equals(Role.DIRECTOR)){
            throw new BlockedException("You have no permission");
        }
        productService.delete(code, token);
        return "Done!";
    }
}
