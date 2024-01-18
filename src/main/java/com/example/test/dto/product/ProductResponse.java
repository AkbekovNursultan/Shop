package com.example.test.dto.product;

import com.example.test.entity.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
    private Long id;
    private String name;
    private String category;
    private Integer price;
}
