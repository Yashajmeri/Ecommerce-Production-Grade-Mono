package com.example.ecommerce.Project1.service;

import com.example.ecommerce.Project1.model.Category;
import com.example.ecommerce.Project1.payload.CategoryDTO;
import com.example.ecommerce.Project1.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize,String sortBy,String sortOrder);
    public CategoryDTO CreateCategory(CategoryDTO categoryDTO);
    public CategoryDTO DeleteCategory(Long categoryId);
    public CategoryDTO UpdateCategory(CategoryDTO categoryDTO,Long categoryId);
}
