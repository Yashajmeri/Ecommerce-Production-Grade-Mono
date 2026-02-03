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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    AuthUtil authUtils;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        // Find the existing cart or create one
       Cart cart = createCart();
        //Retrieve product details
         Product product = productRepository.findById(productId)
                 .orElseThrow(() -> new ResourceNotFoundException("Product not found","productId",productId));
        //Perform Validations
        CartItem cartItem =cartItemRepository.findCartItemByProductIdaAndCartId(cart.getCartId(),productId);
        if(cartItem != null) throw new APIException("product "+product.getProductName()+" already exist");
        if (product.getQuantity() ==0) throw new APIException("product "+product.getProductName()+" is not available");
        if(product.getQuantity() < quantity) {
            throw new APIException("Please , make an order of the" + product.getProductName() +
                    " less than or equal to the quantity "
                    +product.getQuantity());
        }

        //Create cart Item
        CartItem newCartItem = new CartItem();
        newCartItem.setCart(cart);
        newCartItem.setProduct(product);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductPrice(product.getSpecialPrice());
        cartItemRepository.save(newCartItem);
        product.setQuantity(product.getQuantity()); // We will later reduce the stock when the order is placed!! not now
        cart.setTotalPrice(cart.getTotalPrice()+(product.getSpecialPrice() * quantity));
        cartRepository.save(cart);

        //return updated cart
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

    @Override
    public List<CartDTO> getAllCarts() {
        List<Cart>carts = cartRepository.findAll();
        if(carts.isEmpty()) throw new APIException("No carts found");
        List<CartDTO> cartDTOS  = carts.stream()
                .map(cart-> {
                    CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
//                    List<ProductDTO> productDTOS = cart.getCartItems().stream()
//                            .map(product-> modelMapper.map(product.getProduct(), ProductDTO.class)).toList();
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

    @Override
    public CartDTO getCart(String emailId, Long cartId) {
        Cart cart = cartRepository.findCartByEmailAndCartId(emailId,cartId);
         if(cart == null) throw new ResourceNotFoundException("cart","cartId",cartId);
         CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
         cart.getCartItems().forEach(cartItem -> cartItem.getProduct().setQuantity(cartItem.getQuantity()));

//         List<ProductDTO> productDTOS = cart.getCartItems().stream()
//                 .map(product-> modelMapper.map(product, ProductDTO.class)).toList();
        List<ProductDTO> productDTOS = cart.getCartItems().stream()
                .map(cartItem -> {
                    ProductDTO productDTO = modelMapper.map(cartItem.getProduct(), ProductDTO.class);
                    productDTO.setQuantity(cartItem.getQuantity());
                    return productDTO;
                }).toList();
         cartDTO.setProducts(productDTOS);
        return cartDTO;
    }


    @Transactional
    @Override
    public CartDTO updateProductQuantityInCart(Long productId, Integer quantity) {
        String emailId =authUtils.loggedInEmail();
        Cart userCart = cartRepository.findCartByEmail(emailId);

//        System.out.println("YASH_DEBUG"+userCart.toString());
          Long cartId =userCart.getCartId();
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("cart","cartId",cartId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found","productId",productId));
//        System.out.println("YASH_DEBUG :"+product.getQuantity());
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
//            System.out.println("YASH_DEBUG : " + cartItem.getQuantity()+" " + quantity);
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
