package com.example.test.service.impl;

import com.example.test.dto.product.ProductAddRequest;
import com.example.test.dto.product.ProductResponse;
import com.example.test.entity.*;
import com.example.test.enums.Role;
import com.example.test.exception.BadRequestException;
import com.example.test.exception.NotFoundException;
import com.example.test.mapper.ProductMapper;
import com.example.test.repository.*;
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
    private final CustomerRepository customerRepository;
    private final PurchasesRepository purchaseRepository;
    @Override
    public String addNewProduct(ProductAddRequest request, String token) {
        if (productRepository.findByCode(request.getCode()).isPresent())
            throw new NotFoundException("Product with this code is already in storage!: "+request.getCode(),
                    HttpStatus.BAD_REQUEST);
        if (!authService.getUsernameFromToken(token).getRole().equals(Role.DIRECTOR))
            throw new BadCredentialsException("This function only for Director!");

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
        productRepository.save(product);
        return "Success";
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
    public void buy(String code, Integer amount, String token) {
        User user = authService.getUsernameFromToken(token);
        if(!user.getRole().equals(Role.CUSTOMER)){
            throw new BadRequestException("You can't do this");
        }
        Customer customer = user.getCustomer();
        Optional<Product> product = productRepository.findByCode(code);
        if(product.isEmpty()){
            throw new NotFoundException("Code is invalid", HttpStatus.NOT_FOUND);
        }
        if(product.get().getQuantity().equals(0) || product.get().getQuantity()-amount < 0)
            throw new BadRequestException("Out of stock.");
        if(customer.getBalance() < amount * product.get().getPrice()){
            throw new BadRequestException("Not enough balance.");
        }
        customer.setBalance(customer.getBalance() - amount * product.get().getPrice());
        List<Product> products = new ArrayList<>();
        if(!customer.getProducts().isEmpty()){
            products = customer.getProducts();
        }
        product.get().setQuantity(product.get().getQuantity()-amount);
        if(!customer.getProducts().contains(product)){
            products.add(product.get());
        }
//
        List<Purchase> newPurchases = new ArrayList<>();
        Purchase purchase = new Purchase();
        if(!customer.getPurchases().isEmpty()){
            newPurchases = customer.getPurchases();
        }

        if(!customer.getPurchases().contains(purchase)){
            newPurchases.add(purchase);
        }

        product.get().setPurchases(newPurchases);
        customer.setProducts(products);
        purchase.setCustomer(customer);
        purchase.setProducts(products);
        purchase.setQuantity(amount);
        purchase.setCost(amount * product.get().getPrice());

        userRepository.save(user);
    }

    @Override
    public void restock(String code, Integer amount, String token) {
        User user = authService.getUsernameFromToken(token);
        if(!user.getRole().equals(Role.WORKER)){
            throw new BadRequestException("You can't do this");
        }
        Optional <Product> product = productRepository.findByCode(code);
        if(product.isEmpty())
            throw new NotFoundException("Code is invalid.", HttpStatus.NOT_FOUND);
        product.get().setQuantity(product.get().getQuantity() + amount);
        productRepository.save(product.get());
    }
}
