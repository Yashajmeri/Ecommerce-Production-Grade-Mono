package com.example.ecommerce.Project1.service;

import com.example.ecommerce.Project1.exception.APIException;
import com.example.ecommerce.Project1.exception.ResourceNotFoundException;
import com.example.ecommerce.Project1.model.*;
import com.example.ecommerce.Project1.payload.OrderDTO;
import com.example.ecommerce.Project1.payload.OrderItemDTO;
import com.example.ecommerce.Project1.repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the order service impl component.
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements  OrderService{
    private final CartRepository cartRepository;
    private final AddressRepository addressRepository;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    /**
     * Places order.
     * @param currentEmail the currentEmail value.
     * @param addressId the addressId value.
     * @param paymentMethod the paymentMethod value.
     * @param pgName the pgName value.
     * @param pgPaymentId the pgPaymentId value.
     * @param pgStatus the pgStatus value.
     * @param pgResponseMessage the pgResponseMessage value.
     * @return the result of place order.
     */
    @Override
    @Transactional
    public OrderDTO placeOrder(String currentEmail,Long addressId,
                               String paymentMethod, String pgName,
                               String pgPaymentId, String pgStatus,
                               String pgResponseMessage) {
        Cart cart = cartRepository.findCartByEmail(currentEmail);
        System.out.println("Caurrent cart id : " + cart.getCartId());
        if(cart==null) throw new ResourceNotFoundException("cart","email",currentEmail);
        Address address =  addressRepository.findById(addressId).orElseThrow(()-> new ResourceNotFoundException("address","id",addressId));

        Order order = new Order();
        order.setEmail(currentEmail);
        order.setOrderDate(LocalDate.now());
        order.setTotalAmount(cart.getTotalPrice());
        order.setOrderStatus("Order Accepted ! ");
        order.setAddress(address);
        Payment payment = new Payment(paymentMethod,pgPaymentId,pgStatus,pgResponseMessage,pgName);
        payment.setOrder(order);
        payment= paymentRepository.save(payment);
        order.setPayment(payment);
        Order savedOrder = orderRepository.save(order);

        List<CartItem> cartItems = cart.getCartItems();
        if(cartItems.isEmpty()) throw new APIException("Cart is empty !");
        List<OrderItem> orderItems = new ArrayList<>();
            for(CartItem cartItem : cartItems) {
                OrderItem orderItem = new OrderItem();
                orderItem.setDiscount(cartItem.getDiscount());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setProduct(cartItem.getProduct());
                orderItem.setOrderedProductPrice(cartItem.getProductPrice());
                orderItem.setOrder(savedOrder);
                orderItems.add(orderItem);
            }
            orderItems = orderItemRepository.saveAll(orderItems); // return the list of saved orderItems
           cart.getCartItems().forEach(cartItem -> {
                int quantity = cartItem.getQuantity();
                Product product = cartItem.getProduct();
                product.setQuantity(product.getQuantity() - quantity);
                 productRepository.save(product);
               cartService.deleteProductFromCart(cartItem.getProduct().getProductId(),cart.getCartId());
           });
        OrderDTO orderDTO = modelMapper.map(savedOrder, OrderDTO.class);
          orderItems.forEach(orderItem -> { orderDTO.getOrderItems()
                  .add(modelMapper.map(orderItem, OrderItemDTO.class));
          });
          orderDTO.setAddressId(addressId);
        return orderDTO;
    }
}
