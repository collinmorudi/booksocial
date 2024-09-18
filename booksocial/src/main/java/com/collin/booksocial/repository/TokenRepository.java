package com.collin.booksocial.repository;

import com.collin.booksocial.user.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing Token entities.
 *
 * This interface extends JpaRepository providing several methods
 * for working with Token persistence, including methods for saving,
 * deleting, and finding Token entities.
 *
 * The custom method findByToken(String token) is used to find a
 * Token entity based on the token string.
 */
public interface TokenRepository extends JpaRepository<Token, Integer> {

    Optional findByToken(String token);
}
