package com.example.ecommerce.Project1.repositories;

import com.example.ecommerce.Project1.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Defines the contract for address repository operations.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
