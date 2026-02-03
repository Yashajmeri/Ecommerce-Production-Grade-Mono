package com.example.ecommerce.Project1.controller;

import com.example.ecommerce.Project1.config.AppConstant;
import com.example.ecommerce.Project1.model.Category;
import com.example.ecommerce.Project1.payload.CategoryDTO;
import com.example.ecommerce.Project1.payload.CategoryResponse;
import com.example.ecommerce.Project1.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
//     //  Contructor - DependencyInjection
//     public CategoryController(CategoryService categoryService) {
//         this.categoryService = categoryService;
//     }

    @GetMapping("/api/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(@RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER,required = false) Integer PageNumber,
                                                             @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE,required = false) Integer PageSize,
                                                             @RequestParam(name="sortBy",defaultValue = AppConstant.SORT_CATEGORIES_BY,required = false) String sortBy ,
                                                             @RequestParam(name="sortOrder",defaultValue = AppConstant.SORT_DIR,required = false) String sortOrder){
        return ResponseEntity.ok(categoryService.getAllCategories(PageNumber, PageSize, sortBy, sortOrder));
    }

    @PostMapping("/api/public/categories")
    public ResponseEntity<CategoryDTO> CtreateCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategoryDTO = categoryService.CreateCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategoryDTO);
    }

    @DeleteMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> DeleteCategory(@PathVariable Long categoryId) {
        // cleaned the Controller by removing try - catch  : video 106
        return new ResponseEntity<>(categoryService.DeleteCategory(categoryId), HttpStatus.OK);


    }

    @PutMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> UpdateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Long categoryId) {

        CategoryDTO updatedCategoryDTO = categoryService.UpdateCategory(categoryDTO, categoryId);
        return new ResponseEntity<>(updatedCategoryDTO, HttpStatus.OK);

    }
}
