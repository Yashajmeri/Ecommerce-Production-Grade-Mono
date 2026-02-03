package com.example.ecommerce.Project1.controller;

import com.example.ecommerce.Project1.payload.OrderDTO;
import com.example.ecommerce.Project1.payload.OrderRequestDTO;
import com.example.ecommerce.Project1.service.OrderService;
import com.example.ecommerce.Project1.util.AuthUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {
    private final OrderService orderService;
    private final AuthUtil authUtil;

    public  OrderController(OrderService orderService , AuthUtil authUtil) {
        this.orderService = orderService;
        this.authUtil = authUtil;
    }

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
