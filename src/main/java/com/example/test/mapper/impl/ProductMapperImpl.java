package com.example.test.mapper.impl;

import com.example.test.dto.product.ProductResponse;
import com.example.test.entity.Courier;
import com.example.test.entity.Product;
import com.example.test.entity.Worker;
import com.example.test.mapper.ProductMapper;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProductMapperImpl implements ProductMapper {


    @Override
    public ProductResponse toDto(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setName(product.getName());
        productResponse.setId(product.getId());
        productResponse.setCategory(product.getCategory());
        productResponse.setQuantity(product.getQuantity());
        productResponse.setOrderedBy(product.getOrderedBy().getName());
        productResponse.setDeliveredBy(product.getDeliveredBy().getName());
        return productResponse;
    }

    @Override
    public List<ProductResponse> toDtoS(List<Product> all) {
        List<ProductResponse> productResponses = new ArrayList<>();
        for(Product product : all){
            productResponses.add(toDto(product));
        }
        return productResponses;
    }
}
