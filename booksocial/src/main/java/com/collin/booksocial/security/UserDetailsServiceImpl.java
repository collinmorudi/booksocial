package com.collin.booksocial.security;

import com.collin.booksocial.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for loading user details.
 *
 * This class is marked as a Spring service and implements the UserDetailsService interface.
 * It is used for fetching user details based on the user's email.
 *
 * The repository dependency is injected via constructor injection.
 * The loadUserByUsername method is overridden to retrieve user details by email.
 * If a user is not found with the provided email, a UsernameNotFoundException is thrown.
 *
 * Any exception that occurs during the retrieval process is wrapped in a RuntimeException.
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    /**
     * UserRepository instance used for accessing user data from the database.
     * This repository is utilized within the service to find user details by email.
     * Injected via constructor injection.
     */
    private final UserRepository repository;

    /**
     * Loads the user details by their email.
     *
     * @param userEmail the email address of the user to be loaded.
     * @return the UserDetails of the user associated with the provided email.
     * @throws UsernameNotFoundException if no user is found with the provided email.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        try {
            return (UserDetails) repository.findByEmail(userEmail)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
