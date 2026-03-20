package com.example.ecommerce.Project1.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the order item dto component.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private Long  orderItemId;
    private ProductDTO product;
    private Integer Quantity;
    private double discount;
    private  double orderedProductPrice;
}
