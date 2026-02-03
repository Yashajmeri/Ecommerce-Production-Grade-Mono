package com.example.ecommerce.Project1.service;

import com.example.ecommerce.Project1.payload.OrderDTO;
import com.example.ecommerce.Project1.payload.OrderRequestDTO;
import jakarta.transaction.Transactional;


public interface OrderService {
    @Transactional
    public OrderDTO placeOrder(String currentEmail, Long addressId, String paymentMethod, String pgName, String pgPaymentId, String paStatus, String pgResponseMessage);
}
