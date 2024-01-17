package com.example.test.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProductAddRequest {
    private String name;
    private String code;
    private String category;
    private Integer price;
}
