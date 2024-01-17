package com.example.test.repository;

import com.example.test.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String category);
}
