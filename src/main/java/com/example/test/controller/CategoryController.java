package com.example.test.controller;

import com.example.test.dto.category.CategoryResponse;
import com.example.test.repository.CategoryRepository;
import com.example.test.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    @PostMapping("/add")
    public String addCategory(@RequestParam String name, @RequestHeader("Authorization") String token){
        return categoryService.addCategory(name, token);
    }
    @GetMapping("/getAll")
    public List<CategoryResponse> typeResponses(){
        return categoryService.getAll();
    }
}
