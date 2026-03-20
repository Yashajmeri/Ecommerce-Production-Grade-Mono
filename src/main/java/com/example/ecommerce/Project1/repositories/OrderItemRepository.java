package com.example.ecommerce.Project1.repositories;

import com.example.ecommerce.Project1.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Defines the contract for order item repository operations.
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
