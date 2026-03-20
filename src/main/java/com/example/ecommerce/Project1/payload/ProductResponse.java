package com.example.ecommerce.Project1.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents the product response component.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private List<ProductDTO> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Long totalPages;
    private boolean lastPage;
}
