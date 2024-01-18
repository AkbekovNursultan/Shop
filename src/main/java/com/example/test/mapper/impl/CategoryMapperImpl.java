package com.example.test.mapper.impl;

import com.example.test.dto.category.CategoryResponse;
import com.example.test.entity.Category;
import com.example.test.mapper.CategoryMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CategoryMapperImpl implements CategoryMapper {
    @Override
    public List<CategoryResponse> toDtoS(List<Category> all) {
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        for(Category category : all){
            categoryResponses.add(toDto(category));
        }
        return categoryResponses;
    }

    private CategoryResponse toDto(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        return categoryResponse;
    }
}
