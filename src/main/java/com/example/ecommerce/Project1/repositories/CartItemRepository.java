package com.example.ecommerce.Project1.repositories;

import com.example.ecommerce.Project1.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Defines the contract for cart item repository operations.
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    /**
     * Finds cart item by product ida and cart id.
     * @param cartId the cartId value.
     * @param productId the productId value.
     * @return the result of find cart item by product ida and cart id.
     */
    @Query("SELECT ci FROM CartItem ci where ci.cart.id= ?1 AND ci.product.id =?2")
    CartItem findCartItemByProductIdaAndCartId(Long cartId, Long productId);
    /**
     * Deletes cart item by product id and cart id.
     * @param productId the productId value.
     * @param cartId the cartId value.
     */
    @Modifying
    @Query("DELETE  FROM CartItem ci where ci.product.id= ?1 AND ci.cart.id =?2")
    void deleteCartItemByProductIdAndCartId(Long productId, Long cartId);
}
