package com.example.test.service.impl;

import com.example.test.dto.category.CategoryResponse;
import com.example.test.entity.Category;
import com.example.test.enums.Role;
import com.example.test.exception.BadCredentialsException;
import com.example.test.exception.BadRequestException;
import com.example.test.mapper.CategoryMapper;
import com.example.test.repository.CategoryRepository;
import com.example.test.service.AuthService;
import com.example.test.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final AuthService authService;
    private final CategoryMapper categoryMapper;
    @Override
    public void addCategory(String name, String token) {
        Category category = new Category();
        if(!authService.getUsernameFromToken(token).getRole().equals(Role.DIRECTOR))
            throw new BadCredentialsException("You have no permission");
        if(categoryRepository.findByName(name).isPresent())
            throw new BadRequestException("It is already added");
        category.setName(name);
        categoryRepository.save(category);
    }

    @Override
    public List<CategoryResponse> getAll() {
        return categoryMapper.toDtoS(categoryRepository.findAll());
    }
}
