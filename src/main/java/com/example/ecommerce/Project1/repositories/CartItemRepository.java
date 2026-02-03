package com.example.ecommerce.Project1.repositories;

import com.example.ecommerce.Project1.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("SELECT ci FROM CartItem ci where ci.cart.id= ?1 AND ci.product.id =?2")
    CartItem findCartItemByProductIdaAndCartId(Long cartId, Long productId);
    @Modifying
    @Query("DELETE  FROM CartItem ci where ci.product.id= ?1 AND ci.cart.id =?2")
    void deleteCartItemByProductIdAndCartId(Long productId, Long cartId);
}
