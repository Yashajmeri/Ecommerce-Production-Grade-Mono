package com.example.ecommerce.Project1.controller;

import com.example.ecommerce.Project1.payload.OrderDTO;
import com.example.ecommerce.Project1.payload.OrderRequestDTO;
import com.example.ecommerce.Project1.service.OrderService;
import com.example.ecommerce.Project1.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Represents the order controller component.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final AuthUtil authUtil;


    /**
     * Executes order products.
     * @param paymentMethod the paymentMethod value.
     * @param orderRequestDTO the orderRequestDTO value.
     * @return the result of order products.
     */
    @PostMapping("/order/users/payments/{paymentMethod}")
    public ResponseEntity<OrderDTO> orderProducts(@PathVariable String paymentMethod , @RequestBody OrderRequestDTO orderRequestDTO) {
          String currentEmail = authUtil.loggedInEmail();
          OrderDTO order = orderService.placeOrder(currentEmail,
                  orderRequestDTO.getAddressId(),
                  paymentMethod,
                  orderRequestDTO.getPgName(),
                  orderRequestDTO.getPgPaymentId(),
                  orderRequestDTO.getPgStatus(),
                  orderRequestDTO.getPgResponseMessage());

          return new ResponseEntity<> (order, HttpStatus.CREATED);
    }
}
