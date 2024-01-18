package com.example.test.service.impl;

import com.example.test.dto.product.ProductAddRequest;
import com.example.test.dto.product.ProductResponse;
import com.example.test.entity.Category;
import com.example.test.entity.Product;
import com.example.test.entity.User;
import com.example.test.enums.Role;
import com.example.test.exception.BadRequestException;
import com.example.test.exception.NotFoundException;
import com.example.test.mapper.ProductMapper;
import com.example.test.repository.CategoryRepository;
import com.example.test.repository.ProductRepository;
import com.example.test.repository.UserRepository;
import com.example.test.service.AuthService;
import com.example.test.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final AuthService authService;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final UserRepository userRepository;
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
        product.setQuantity(request.getQuantity());
        product.setCode(request.getCode());
        product.setCreated_date(LocalDateTime.now().toString());
        Optional<Category> category = categoryRepository.findByName(request.getCategory());
        if (category.isEmpty())
            throw new NotFoundException("This category is not here", HttpStatus.BAD_REQUEST);
        product.setCategory(category.get());
        List<Product> products = new ArrayList<>();
        if(!product.getCategory().getProducts().isEmpty()){
            products = product.getCategory().getProducts();
        }
        products.add(product);
        category.get().setProducts(products);
        //product.getCategory().setProducts(products);
        productRepository.save(product);
    }

    @Override
    public List<ProductResponse> getAll(String category) {
        if(categoryRepository.findByName(category).isEmpty()){
            throw new NotFoundException("This category doesn't exist", HttpStatus.NOT_FOUND);
        }
        List<ProductResponse> productResponses = productMapper.toDtoS(productRepository.findAllByCategory(categoryRepository.findByName(category).get()));
        return productResponses;
    }

    @Override
    public void buy(String code, String token) {
        User user = authService.getUsernameFromToken(token);
        Product product = productRepository.findByCode(code).get();
        if(product.getQuantity().equals(0))
            throw new BadRequestException("Out of stocks");
        List<Product> products = new ArrayList<>();
        if(!user.getCustomer().getProducts().isEmpty()){
            products = user.getCustomer().getProducts();
        }
        product.setQuantity(product.getQuantity()-1);
        products.add(product);
        user.getCustomer().setProducts(products);
        userRepository.save(user);
    }

    @Override
    public void deleteById(Long id, String token) {
        if(authService.getUsernameFromToken(token).getRole().equals(Role.DIRECTOR))
            productRepository.deleteById(id);
        else{
            throw new BadRequestException("You have no permission");
        }
    }
}
