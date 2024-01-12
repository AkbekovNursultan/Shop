package com.example.test.service.impl;

import com.example.test.dto.product.ProductRequest;
import com.example.test.dto.product.ProductResponse;
import com.example.test.entity.Product;
import com.example.test.enums.Category;
import com.example.test.exception.NotFoundException;
import com.example.test.mapper.ProductMapper;
import com.example.test.repository.ProductRepository;
import com.example.test.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    @Override
    public ProductResponse getById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            throw new NotFoundException("Doesn't exist", HttpStatus.NOT_FOUND);
        }
        return productMapper.toDto(product.get());
    }

    @Override
    public List<ProductResponse> getAll() {
        return productMapper.toDtoS(productRepository.findAll());
    }

    @Override
    public void register(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        if(!containsCategory(productRequest.getCategory())){
            throw new NotFoundException("This category doesn't exist", HttpStatus.NOT_FOUND);
        }
        product.setCategory(Category.valueOf(productRequest.getCategory()));
        product.setQuantity(productRequest.getQuantity());
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        if(productRepository.findById(id).isEmpty()){
            throw new NotFoundException("This product doesn't exist", HttpStatus.NOT_FOUND);
        }
        productRepository.deleteById(id);
    }


    public boolean containsCategory(String s){
        for(Category category : Category.values()){
            if(category.name().equalsIgnoreCase(s)){
                return true;
            }
        }
        return false;
    }
}
