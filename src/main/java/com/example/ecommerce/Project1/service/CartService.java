package com.example.ecommerce.Project1.service;

import com.example.ecommerce.Project1.payload.CartDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Defines the contract for cart service operations.
 */
public interface CartService {
    /**
     * Adds product to cart.
     * @param productId the productId value.
     * @param quantity the quantity value.
     * @return the result of add product to cart.
     */
    CartDTO addProductToCart(Long productId, Integer quantity);

    /**
     * Returns the all carts.
     * @return the all carts.
     */
    List<CartDTO> getAllCarts();

    /**
     * Returns the cart.
     * @param emailId the emailId value.
     * @param cartId the cartId value.
     * @return the cart.
     */
    CartDTO getCart(String emailId, Long cartId);

    /**
     * Updates product quantity in cart.
     * @param productId the productId value.
     * @param quantity the quantity value.
     * @return the result of update product quantity in cart.
     */
    @Transactional
    CartDTO updateProductQuantityInCart(Long productId, Integer quantity);

    /**
     * Deletes product from cart.
     * @param productId the productId value.
     * @param cartId the cartId value.
     * @return the result of delete product from cart.
     */
    String deleteProductFromCart(Long productId, Long cartId);

    /**
     * Updates product in carts.
     * @param cartId the cartId value.
     * @param productId the productId value.
     */
    void updateProductInCarts(Long cartId,Long productId);
}
