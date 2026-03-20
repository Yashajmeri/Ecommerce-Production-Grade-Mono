package com.example.ecommerce.Project1.controller;


import com.example.ecommerce.Project1.model.Cart;
import com.example.ecommerce.Project1.payload.CartDTO;
import com.example.ecommerce.Project1.repositories.CartRepository;
import com.example.ecommerce.Project1.service.CartService;
import com.example.ecommerce.Project1.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Represents the cart controller component.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final AuthUtil authUtil;
    private final CartRepository cartRepository;

    /**
     * Adds product to cart.
     * @param productId the productId value.
     * @param quantity the quantity value.
     * @return the result of add product to cart.
     */
    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId,
                                                    @PathVariable Integer quantity) {
        CartDTO cartDTO = cartService.addProductToCart(productId, quantity);
        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.CREATED);
    }
    /**
     * Returns the carts.
     * @return the carts.
     */
    @GetMapping("/carts")
    public ResponseEntity<List<CartDTO>> getCarts() {
        List<CartDTO> cartDTOS =cartService.getAllCarts();
        return  new ResponseEntity<>(cartDTOS, HttpStatus.FOUND);
    }
    /**
     * Returns the carts by user.
     * @return the carts by user.
     */
    @GetMapping("/carts/users/cart")
    public ResponseEntity<CartDTO> getCartsByUser() {
        String emailId = authUtil.loggedInEmail();
        Cart cart = cartRepository.findCartByEmail(emailId);
        Long cartId = cart.getCartId();
        CartDTO cartDTO = cartService.getCart(emailId,cartId);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }
    /**
     * Updates cart product.
     * @param productId the productId value.
     * @param operation the operation value.
     * @return the result of update cart product.
     */
    @PutMapping("/cart/products/{productId}/quantity/{operation}")
    public  ResponseEntity<CartDTO> updateCartProduct(@PathVariable Long productId, @PathVariable String operation) {

        CartDTO cartDTO = cartService.updateProductQuantityInCart(productId,operation.equalsIgnoreCase("delete") ? -1 : 1);

        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }
    /**
     * Deletes product from the cart.
     * @param productId the productId value.
     * @param cartId the cartId value.
     * @return the result of delete product from the cart.
     */
    @DeleteMapping("/carts/{cartId}/product/{productId}")
    public ResponseEntity<String> deleteProductFromTheCart(@PathVariable Long productId,@PathVariable Long cartId) {
        String status = cartService.deleteProductFromCart(productId,cartId);
        return new ResponseEntity<>(status,HttpStatus.OK);
    }
}
