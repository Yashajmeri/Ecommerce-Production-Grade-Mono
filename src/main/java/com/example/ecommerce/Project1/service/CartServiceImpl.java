package com.example.ecommerce.Project1.service;

import com.example.ecommerce.Project1.exception.APIException;
import com.example.ecommerce.Project1.exception.ResourceNotFoundException;
import com.example.ecommerce.Project1.model.Cart;
import com.example.ecommerce.Project1.model.CartItem;
import com.example.ecommerce.Project1.model.Product;
import com.example.ecommerce.Project1.payload.CartDTO;
import com.example.ecommerce.Project1.payload.ProductDTO;
import com.example.ecommerce.Project1.repositories.CartItemRepository;
import com.example.ecommerce.Project1.repositories.CartRepository;
import com.example.ecommerce.Project1.repositories.ProductRepository;
import com.example.ecommerce.Project1.util.AuthUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents the cart service impl component.
 */
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final AuthUtil authUtils;
    private final ModelMapper modelMapper;
    /**
     * Adds product to cart.
     * @param productId the productId value.
     * @param quantity the quantity value.
     * @return the result of add product to cart.
     */
    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {
       Cart cart = createCart();
         Product product = productRepository.findById(productId)
                 .orElseThrow(() -> new ResourceNotFoundException("Product not found","productId",productId));
        CartItem cartItem =cartItemRepository.findCartItemByProductIdaAndCartId(cart.getCartId(),productId);
        if(cartItem != null) throw new APIException("product "+product.getProductName()+" already exist");
        if (product.getQuantity() ==0) throw new APIException("product "+product.getProductName()+" is not available");
        if(product.getQuantity() < quantity) {
            throw new APIException("Please , make an order of the" + product.getProductName() +
                    " less than or equal to the quantity "
                    +product.getQuantity());
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setCart(cart);
        newCartItem.setProduct(product);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductPrice(product.getSpecialPrice());
        cartItemRepository.save(newCartItem);
        product.setQuantity(product.getQuantity());
        cart.setTotalPrice(cart.getTotalPrice()+(product.getSpecialPrice() * quantity));
        cartRepository.save(cart);

     CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<ProductDTO> productDTOS = cart.getCartItems().stream()
                .map(cI -> {
                    ProductDTO productDTO = modelMapper.map(cI.getProduct(), ProductDTO.class);
                    productDTO.setQuantity(cI.getQuantity());
                    return productDTO;
                }).toList();
        cartDTO.setProducts(productDTOS);
        return cartDTO;
    }

    /**
     * Returns the all carts.
     * @return the all carts.
     */
    @Override
    public List<CartDTO> getAllCarts() {
        List<Cart>carts = cartRepository.findAll();
        if(carts.isEmpty()) throw new APIException("No carts found");
        List<CartDTO> cartDTOS  = carts.stream()
                .map(cart-> {
                    CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
                    List<ProductDTO> productDTOS = cart.getCartItems().stream()
                                    .map(cartItem -> {
                                        ProductDTO productDTO = modelMapper.map(cartItem.getProduct(), ProductDTO.class);
                                        productDTO.setQuantity(cartItem.getQuantity());
                                        return productDTO;
                                    }).toList();
                    cartDTO.setProducts(productDTOS);
                            return cartDTO;
                }).toList();
        return cartDTOS;
    }

    /**
     * Returns the cart.
     * @param emailId the emailId value.
     * @param cartId the cartId value.
     * @return the cart.
     */
    @Override
    public CartDTO getCart(String emailId, Long cartId) {
        Cart cart = cartRepository.findCartByEmailAndCartId(emailId,cartId);
         if(cart == null) throw new ResourceNotFoundException("cart","cartId",cartId);
         CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
         cart.getCartItems().forEach(cartItem -> cartItem.getProduct().setQuantity(cartItem.getQuantity()));
        List<ProductDTO> productDTOS = cart.getCartItems().stream()
                .map(cartItem -> {
                    ProductDTO productDTO = modelMapper.map(cartItem.getProduct(), ProductDTO.class);
                    productDTO.setQuantity(cartItem.getQuantity());
                    return productDTO;
                }).toList();
         cartDTO.setProducts(productDTOS);
        return cartDTO;
    }


    /**
     * Updates product quantity in cart.
     * @param productId the productId value.
     * @param quantity the quantity value.
     * @return the result of update product quantity in cart.
     */
    @Transactional
    @Override
    public CartDTO updateProductQuantityInCart(Long productId, Integer quantity) {
        String emailId =authUtils.loggedInEmail();
        Cart userCart = cartRepository.findCartByEmail(emailId);

          Long cartId =userCart.getCartId();
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("cart","cartId",cartId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found","productId",productId));
        if (product.getQuantity() ==0) throw new APIException("product "+product.getProductName()+" is not available");
        if(product.getQuantity() < quantity) {
            throw new APIException("Please , make an order of the" + product.getProductName() +
                    " less than or equal to the quantity "
                    +product.getQuantity());
        }
        CartItem cartItem = cartItemRepository.findCartItemByProductIdaAndCartId(cartId,productId);
        if(cartItem== null){
            throw new APIException("product "+product.getProductName()+" is not available");

        }
        if(cartItem.getQuantity() + quantity < 0) throw new  APIException("Resulting quantity can not be zero!!");
        if(cartItem.getQuantity() + quantity == 0) {
            deleteProductFromCart(productId,cartId);
        } else {
            cartItem.setProductPrice(product.getSpecialPrice());
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setDiscount(product.getDiscount());
            cart.setTotalPrice(cart.getTotalPrice() + (cartItem.getProductPrice() * quantity));
            cartRepository.save(cart);
        }


        CartItem updatedCartItem = cartItemRepository.save(cartItem);
        if(updatedCartItem.getQuantity() == 0 ) {
            cartItemRepository.deleteById(updatedCartItem.getCartItemId());

        }
    CartDTO cartDTO = modelMapper.map(cart,CartDTO.class);
        List<CartItem> cartItems = cart.getCartItems();
        Stream<ProductDTO> productDTOStream = cartItems.stream()
                .map(item-> {
                    ProductDTO productDTO = modelMapper.map(item.getProduct(),ProductDTO.class);
                    productDTO.setQuantity(item.getQuantity());
                    return productDTO;
                });
        cartDTO.setProducts(productDTOStream.toList());
        return cartDTO;
    }

    /**
     * Deletes product from cart.
     * @param productId the productId value.
     * @param cartId the cartId value.
     * @return the result of delete product from cart.
     */
    @Transactional
    @Override
    public String deleteProductFromCart(Long productId, Long cartId) {
          Cart cart = cartRepository.findById(cartId)
                  .orElseThrow(()-> new ResourceNotFoundException("cart","cartId",cartId));
          CartItem cartItem = cartItemRepository.findCartItemByProductIdaAndCartId(cartId,productId);
          if(cartItem == null) throw new ResourceNotFoundException("product","productId",productId);
          cart.setTotalPrice(cart.getTotalPrice()-(cartItem.getProductPrice() * cartItem.getQuantity()));
          cartItemRepository.deleteCartItemByProductIdAndCartId(productId,cartId);
        return "product from cart deleted successfully !";
    }

    /**
     * Updates product in carts.
     * @param cartId the cartId value.
     * @param productId the productId value.
     */
    @Override
    public void updateProductInCarts(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("cart","cartId",cartId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found","productId",productId));
        CartItem cartItem = cartItemRepository.findCartItemByProductIdaAndCartId(cartId,productId);
        if(cartItem == null) throw new APIException("product "+product.getProductName()+" is not available in the cart!!");
        double cartPrice = cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity());
        cartItem.setProductPrice(product.getSpecialPrice());
        cart.setTotalPrice(cartPrice + (cartItem.getProductPrice() * cartItem.getQuantity()));
        cartItem = cartItemRepository.save(cartItem);

    }

    /**
     * Creates cart.
     * @return the result of create cart.
     */
    Cart createCart() {
        Cart userCart =cartRepository.findCartByEmail(authUtils.loggedInEmail());
        if(userCart != null) {
            return userCart;
        }
        Cart  cart =new Cart();
        cart.setTotalPrice(0.00);
        cart.setUser(authUtils.loggedInUser());
        return cartRepository.save(cart);
    }
}
