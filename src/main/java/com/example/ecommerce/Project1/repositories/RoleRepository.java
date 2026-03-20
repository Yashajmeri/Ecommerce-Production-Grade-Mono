package com.example.ecommerce.Project1.repositories;

import com.example.ecommerce.Project1.model.AppRole;
import com.example.ecommerce.Project1.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Defines the contract for role repository operations.
 */
public interface RoleRepository  extends JpaRepository  <Role,Long> {
    /**
     * Finds by role name.
     * @param roleName the roleName value.
     * @return the result of find by role name.
     */
    Optional<Role> findByRoleName(AppRole roleName);
}
