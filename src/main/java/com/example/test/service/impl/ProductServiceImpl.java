package com.example.test.service.impl;

import com.example.test.dto.product.ProductAddRequest;
import com.example.test.entity.Category;
import com.example.test.entity.Product;
import com.example.test.enums.Role;
import com.example.test.exception.NotFoundException;
import com.example.test.repository.ProductCategoryRepository;
import com.example.test.repository.ProductRepository;
import com.example.test.service.AuthService;
import com.example.test.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final AuthService authService;
    private final ProductCategoryRepository productCategoryRepository;
    @Override
    public void addProduct(ProductAddRequest request, String token) {
        if (productRepository.findByCode(request.getCode()).isPresent())
            throw new NotFoundException("book with this transcript is already exist!: "+request.getCode(),
                    HttpStatus.BAD_REQUEST);
        if (!authService.getUsernameFromToken(token).getRole().equals(Role.DIRECTOR))
            throw new BadCredentialsException("this function only for admin!");

        Product product = new Product();

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantity(0);
        product.setCode(request.getCode());
        product.setCreated_date(LocalDateTime.now().toString());
//        Optional<Category> category = productCategoryRepository.findByName(request.getCategory());
//        if (category.isEmpty())
//            throw new NotFoundException("sjhbf", HttpStatus.BAD_REQUEST);
//        product.setCategory(category.get());
        productRepository.save(product);
    }
}
