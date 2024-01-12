package com.example.test.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProductRequest {
    private String name;
    private String category;
    private Integer quantity;
}
