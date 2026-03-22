package com.example.ecommerce.Project1.service;

import com.example.ecommerce.Project1.model.Category;
import com.example.ecommerce.Project1.payload.CategoryDTO;
import com.example.ecommerce.Project1.payload.CategoryResponse;

import java.util.List;

/**
 * Defines the contract for category service operations.
 */
public interface CategoryService {
    /**
     * Returns the all categories.
     * @param pageNumber the pageNumber value.
     * @param pageSize the pageSize value.
     * @param sortBy the sortBy value.
     * @param sortOrder the sortOrder value.
     * @return the all categories.
     */
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize,String sortBy,String sortOrder);
    /**
     * Creates category.
     * @param categoryDTO the categoryDTO value.
     * @return the result of create category.
     */
    public CategoryDTO createCategory(CategoryDTO categoryDTO);
    /**
     * Deletes category.
     * @param categoryId the categoryId value.
     * @return the result of delete category.
     */
    public CategoryDTO deleteCategory(Long categoryId);
    /**
     * Updates category.
     * @param categoryDTO the categoryDTO value.
     * @param categoryId the categoryId value.
     * @return the result of update category.
     */
    public CategoryDTO updateCategory(CategoryDTO categoryDTO,Long categoryId);
}
