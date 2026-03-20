package com.example.ecommerce.Project1.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the cart dto component.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Long CartId;
    private Double totalPrice=0.0;
    /**
     * Executes array list<>.
     * @return the result of array list<>.
     */
    private List<ProductDTO> products = new ArrayList<>();

}
