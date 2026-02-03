package com.example.ecommerce.Project1.repositories;

import com.example.ecommerce.Project1.model.AppRole;
import com.example.ecommerce.Project1.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository  extends JpaRepository  <Role,Long> {
    Optional<Role> findByRoleName(AppRole roleName);
}
