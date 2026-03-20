package com.example.ecommerce.Project1.repositories;

import com.example.ecommerce.Project1.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Defines the contract for user repository operations.
 */
@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
    /**
     * Finds by username.
     * @param username the username value.
     * @return the result of find by username.
     */
    Optional<User> findByUsername(String username);

    /**
     * Executes exists by username.
     * @param username the username value.
     * @return the result of exists by username.
     */
    boolean existsByUsername( String username);

    /**
     * Executes exists by email.
     * @param Email the Email value.
     * @return the result of exists by email.
     */
    boolean existsByEmail(String Email);
}
