package com.collin.booksocial.repository;

import com.collin.booksocial.user.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    Optional findByToken(String token);
}
