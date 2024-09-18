package com.collin.booksocial.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling authentication-related operations.
 */
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

    /**
     * Service responsible for handling authentication operations such as user registration,
     * authentication, and account activation.
     */
    private final  AuthenticationService service;

    /**
     * Handles the registration process for new users.
     *
     * @param request the registration request object containing the user's details
     * @return a ResponseEntity indicating the registration status
     * @throws MessagingException if there is an issue with sending a confirmation email
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(
            @RequestBody @Valid RegistrationRequest request
    ) throws MessagingException {
        service.register(request);
        return ResponseEntity.accepted().build();
    }

    /**
     * Authenticates a user based on the provided authentication request.
     *
     * @param request the authentication request object containing the user's credentials
     * @return a ResponseEntity containing the authentication response with authentication details
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate (
            @RequestBody @Valid AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    /**
     * Endpoint for activating a user's account using an activation token.
     *
     * @param token the activation token provided in the request
     * @throws Throwable if an error occurs during the activation process
     */
    @GetMapping("/activate-account")
    public void confirm(
            @RequestParam String token
    ) throws Throwable {
        service.activateAccount(token);
    }
}
