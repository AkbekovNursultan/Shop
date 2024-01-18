package com.example.test.mapper;

import com.example.test.dto.category.CategoryResponse;
import com.example.test.entity.Category;

import java.util.List;

public interface CategoryMapper {
    List<CategoryResponse> toDtoS(List<Category> all);
}
