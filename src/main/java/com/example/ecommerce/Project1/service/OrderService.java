package com.example.ecommerce.Project1.service;

import com.example.ecommerce.Project1.payload.OrderDTO;
import com.example.ecommerce.Project1.payload.OrderRequestDTO;
import jakarta.transaction.Transactional;


/**
 * Defines the contract for order service operations.
 */
public interface OrderService {
    /**
     * Places order.
     * @param currentEmail the currentEmail value.
     * @param addressId the addressId value.
     * @param paymentMethod the paymentMethod value.
     * @param pgName the pgName value.
     * @param pgPaymentId the pgPaymentId value.
     * @param paStatus the paStatus value.
     * @param pgResponseMessage the pgResponseMessage value.
     * @return the result of place order.
     */
    @Transactional
    public OrderDTO placeOrder(String currentEmail, Long addressId, String paymentMethod, String pgName, String pgPaymentId, String paStatus, String pgResponseMessage);
}
