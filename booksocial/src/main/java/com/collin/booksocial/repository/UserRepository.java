package com.collin.booksocial.repository;

import com.collin.booksocial.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional findByEmail(String email);
}
