package com.collin.booksocial.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * JwtFilter is a custom Spring Security filter that extends OncePerRequestFilter.
 * It is responsible for filtering incoming HTTP requests and validating JWT tokens.
 *
 * The filter checks if the request contains a JWT token and validates it.
 * If the token is valid, it retrieves the user details and sets the authentication
 * in the SecurityContextHolder.
 *
 * JwtFilter skips the filtering for requests to the authorization endpoint.
 */
@Service
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    /**
     * JwtService is responsible for managing JSON Web Tokens (JWT), including
     * tasks such as creation, validation, and extraction of user details from tokens.
     *
     * This service is used within JwtFilter to validate incoming JWT tokens
     * and to extract relevant user information from those tokens.
     */
    private final JwtService jwtService;
    /**
     * A service for retrieving user details, used in the JwtFilter to load the
     * user's information from the token and validate the user's credentials.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Filters incoming HTTP requests and validates JWT tokens. If the request contains a valid JWT token,
     * it retrieves user details and sets the authentication in the SecurityContextHolder.
     * Skips filtering for requests to the authorization endpoint.
     *
     * @param request the HttpServletRequest object that contains the request the client made to the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @param filterChain the FilterChain object to pass the request and response to the next entity in the chain
     * @throws ServletException if the request could not be handled
     * @throws IOException if an input or output error is detected when the servlet handles the request
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader(AUTHORIZATION);
        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
