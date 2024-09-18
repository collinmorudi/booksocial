package com.collin.booksocial.auth;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a response containing an authentication token.
 * Typically used to encapsulate the token returned upon successful authentication.
 *
 * Uses Lombok annotations to generate getter and setter methods, and a builder pattern.
 */
@Getter
@Setter
@Builder
public class AuthenticationResponse {

    private String token;
}
