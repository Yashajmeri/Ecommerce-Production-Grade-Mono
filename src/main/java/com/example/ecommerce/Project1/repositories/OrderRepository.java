package com.example.ecommerce.Project1.repositories;

import com.example.ecommerce.Project1.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Defines the contract for order repository operations.
 */
@Repository
public interface OrderRepository  extends JpaRepository<Order, Long> {
}
