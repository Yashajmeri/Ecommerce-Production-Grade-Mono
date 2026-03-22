package com.example.ecommerce.Project1.controller;

import com.example.ecommerce.Project1.config.AppConstant;
import com.example.ecommerce.Project1.payload.CategoryDTO;
import com.example.ecommerce.Project1.payload.CategoryResponse;
import com.example.ecommerce.Project1.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Represents the category controller component.
 */
@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * Returns the all categories.
     * @param PageNumber the PageNumber value.
     * @param PageSize the PageSize value.
     * @param sortBy the sortBy value.
     * @param sortOrder the sortOrder value.
     * @return the all categories.
     */
    @GetMapping("/api/public/categories")
    @PreAuthorize("permitAll()")
    public ResponseEntity<CategoryResponse> getAllCategories(@RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER,required = false) Integer PageNumber,
                                                             @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE,required = false) Integer PageSize,
                                                             @RequestParam(name="sortBy",defaultValue = AppConstant.SORT_CATEGORIES_BY,required = false) String sortBy ,
                                                             @RequestParam(name="sortOrder",defaultValue = AppConstant.SORT_DIR,required = false) String sortOrder){
        return ResponseEntity.ok(categoryService.getAllCategories(PageNumber, PageSize, sortBy, sortOrder));
    }

    /**
     * Executes ctreate category.
     * @param categoryDTO the categoryDTO value.
     * @return the result of ctreate category.
     */
    @PostMapping("/api/public/categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategoryDTO);
    }

    /**
     * Deletes category.
     * @param categoryId the categoryId value.
     * @return the result of delete category.
     */
    @DeleteMapping("/api/admin/categories/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {
        return new ResponseEntity<>(categoryService.deleteCategory(categoryId), HttpStatus.OK);
    }

    /**
     * Updates category.
     * @param categoryDTO the categoryDTO value.
     * @param categoryId the categoryId value.
     * @return the result of update category.
     */
    @PutMapping("/api/admin/categories/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Long categoryId) {

        CategoryDTO updatedCategoryDTO = categoryService.updateCategory(categoryDTO, categoryId);
        return new ResponseEntity<>(updatedCategoryDTO, HttpStatus.OK);

    }
}
