package com.collin.booksocial.repository;

import com.collin.booksocial.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * UserRepository is an interface for performing CRUD operations on User entities.
 * It extends the JpaRepository interface to inherit standard database operations.
 *
 * Methods:
 *
 *
 * findByEmail(String email):
 *     Retrieves an Optional containing a User entity based on the provided email.
 *     If no User is found, the Optional is empty.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional findByEmail(String email);
}
