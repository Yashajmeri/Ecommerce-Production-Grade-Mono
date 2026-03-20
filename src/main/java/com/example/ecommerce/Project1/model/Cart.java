package com.example.ecommerce.Project1.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data

/**
 * Represents the cart component.
 */
@NoArgsConstructor
@AllArgsConstructor
@Table(name="carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    /**
     * Executes array list<>.
     * @return the result of array list<>.
     */
    @OneToMany(mappedBy = "cart",cascade ={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE},orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();
    private Double totalPrice= 0.0;
}
