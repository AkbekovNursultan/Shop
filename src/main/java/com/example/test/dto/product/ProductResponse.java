package com.example.test.dto.product;

import com.example.test.enums.Category;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Category category;
    private Integer quantity;
}
