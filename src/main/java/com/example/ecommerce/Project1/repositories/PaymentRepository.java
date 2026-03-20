package com.example.ecommerce.Project1.repositories;

import com.example.ecommerce.Project1.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Defines the contract for payment repository operations.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
