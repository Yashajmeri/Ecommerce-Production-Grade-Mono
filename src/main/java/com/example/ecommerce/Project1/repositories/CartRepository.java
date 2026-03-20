package com.example.ecommerce.Project1.repositories;

import com.example.ecommerce.Project1.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Defines the contract for cart repository operations.
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    /**
     * Finds cart by email.
     * @param Email the Email value.
     * @return the result of find cart by email.
     */
    @Query("SELECT c FROM Cart c WHERE c.user.email = ?1")
    Cart findCartByEmail (String Email);
    /**
     * Finds cart by email and cart id.
     * @param emailId the emailId value.
     * @param cartId the cartId value.
     * @return the result of find cart by email and cart id.
     */
    @Query("SELECT c FROM Cart c WHERE c.user.email = ?1 AND c.id = ?2")
    Cart findCartByEmailAndCartId(String emailId, Long cartId);
    /**
     * Finds carts by product id.
     * @param productId the productId value.
     * @return the result of find carts by product id.
     */
    @Query("SELECT c FROM Cart c JOIN FETCH c.cartItems ci JOIN FETCH ci.product p WHERE p.id= ?1")
    List<Cart> findCartsByProductId(Long productId);

}
