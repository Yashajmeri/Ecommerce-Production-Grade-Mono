package com.example.ecommerce.Project1.service;

import com.example.ecommerce.Project1.exception.APIException;
import com.example.ecommerce.Project1.exception.ResourceNotFoundException;
import com.example.ecommerce.Project1.model.*;
import com.example.ecommerce.Project1.payload.OrderDTO;
import com.example.ecommerce.Project1.payload.OrderItemDTO;
import com.example.ecommerce.Project1.repositories.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements  OrderService{
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public OrderDTO placeOrder(String currentEmail,Long addressId,
                               String paymentMethod, String pgName,
                               String pgPaymentId, String pgStatus,
                               String pgResponseMessage) {
        // getting the User cart
        Cart cart = cartRepository.findCartByEmail(currentEmail);
        System.out.println("Caurrent cart id : " + cart.getCartId());
        if(cart==null) throw new ResourceNotFoundException("cart","email",currentEmail);
        Address address =  addressRepository.findById(addressId).orElseThrow(()-> new ResourceNotFoundException("address","id",addressId));



        // Create a new Order with the payment info
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

        // Get Cart-items into the Order-items
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
        // upDate the actual stock
           cart.getCartItems().forEach(cartItem -> {
                int quantity = cartItem.getQuantity();
                Product product = cartItem.getProduct();
                product.setQuantity(product.getQuantity() - quantity);
                 productRepository.save(product);
                 // clear the item from the cart
               cartService.deleteProductFromCart(cartItem.getProduct().getProductId(),cart.getCartId());
           });
           // sendBack the order summary
        OrderDTO orderDTO = modelMapper.map(savedOrder, OrderDTO.class);
          orderItems.forEach(orderItem -> { orderDTO.getOrderItems()
                  .add(modelMapper.map(orderItem, OrderItemDTO.class));
          });
          orderDTO.setAddressId(addressId);
        return orderDTO;
    }
}
