package com.collin.booksocial.config;

import com.collin.booksocial.user.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Implementation of AuditorAware used to retrieve the current auditor's identifier.
 *
 * This class is used in the context of auditing to capture the ID of the currently authenticated user.
 * It implements the Spring Data JPA AuditorAware interface.
 */
public class ApplicationAuditAware implements AuditorAware<Integer> {
    /**
     * Retrieves the current auditor's identifier.
     *
     * The method fetches the current authentication details from the security context,
     * verifies the authentication status and returns the ID of the authenticated user
     * wrapped in an Optional. If the user is not authenticated, an empty Optional is returned.
     *
     * @return an Optional containing the current auditor's ID, or an empty Optional if there is no authenticated user.
     */
    @Override
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

         User userPrincipal = (User) authentication.getPrincipal();

        return Optional.ofNullable(userPrincipal.getId());
    }
}
