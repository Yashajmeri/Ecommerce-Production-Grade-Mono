package com.example.ecommerce.Project1.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the category dto component.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private String categoryName;
    private Long categoryId;
}
