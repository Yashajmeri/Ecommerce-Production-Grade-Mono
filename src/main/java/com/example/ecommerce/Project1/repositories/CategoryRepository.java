package com.example.ecommerce.Project1.repositories;

import com.example.ecommerce.Project1.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(@NotBlank @Size(min = 5,message = "Category name must contain atleast 5 characters") String categoryName);
}
