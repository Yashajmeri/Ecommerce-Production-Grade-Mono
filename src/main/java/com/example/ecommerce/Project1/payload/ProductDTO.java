package com.example.ecommerce.Project1.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long ProductId;
    private String ProductName;
    private String image;
    private Integer quantity;
    private String description;
    private double Price;
    private double specialPrice;
    private double Discount;

}
