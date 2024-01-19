package com.example.test.service;

import com.example.test.dto.category.CategoryResponse;

import java.util.List;

public interface CategoryService {
   String addCategory(String name, String token);

   List<CategoryResponse> getAll();
}
