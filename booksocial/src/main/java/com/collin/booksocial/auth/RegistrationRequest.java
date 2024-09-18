package com.collin.booksocial.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Class representing a registration request.
 *
 * This class contains the necessary fields for a user registration process,
 * including firstname, lastname, email, and password.
 */
@Getter
@Setter
@Builder
public class RegistrationRequest {


    /**
     * The firstname of the user.
     *
     * This field is mandatory and cannot be empty or null.
     * It represents the given name of the user in the registration process.
     */
    @NotEmpty(message = "Firstname is mandatory")
    @NotNull(message = "Firstname is mandatory")
    private String firstname;
    /**
     * The last name of the user.
     *
     * This field is mandatory and must not be empty or null.
     */
    @NotEmpty(message = "Lastname is mandatory")
    @NotNull(message = "Lastname is mandatory")
    private String lastname;
    /**
     * The email address of the user.
     *
     * This field is mandatory and must be well formatted according to email conventions.
     * Proper error messages will be displayed if the email is not provided or not correctly formatted.
     */
    @Email(message = "Email is not well formatted")
    @NotEmpty(message = "Email is mandatory")
    @NotNull(message = "Email is mandatory")
    private String email;
    /**
     * Password for user registration.
     *
     * This field is mandatory and must be at least 8 characters long.
     * It must not be null or empty to ensure that the user sets a valid password.
     */
    @NotEmpty(message = "Password is mandatory")
    @NotNull(message = "Password is mandatory")
    @Size(min = 8, message = "Password should be 8 characters long minimum")
    private String password;
}
