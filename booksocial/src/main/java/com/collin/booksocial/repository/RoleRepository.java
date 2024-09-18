package com.collin.booksocial.repository;

import com.collin.booksocial.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * RoleRepository is the interface for Role entity data access operations.
 *
 * This repository extends JpaRepository which provides JPA functionalities
 * for the Role entity, such as CRUD operations.
 *
 * Additionally, RoleRepository provides method to find role based on its name.
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String role);
}
