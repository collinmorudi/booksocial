package com.collin.booksocial.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationRequest {

    /**
     * Represents the email of the user attempting to authenticate.
     * The email must be in a valid format and cannot be null or empty.
     *
     * Constraints:
     * - Must be a valid email format.
     * - Cannot be null or empty.
     *
     * Validation messages:
     * - "Email format is invalid" when the email does not conform to standard email structure.
     * - "Email is mandatory" when the field is null or empty.
     */
    @Email(message = "Email format is invalid")
    @NotEmpty(message = "Email is mandatory")
    @NotNull(message = "Email is mandatory")
    private String email;

    /**
     * The password required for authentication.
     * This field is mandatory and cannot be empty.
     * It must be at least 8 characters long.
     *
     * Validation constraints:
     * - Must not be null
     * - Must not be empty
     * - Must have a minimum length of 8 characters
     */
    @NotEmpty(message = "Password is mandatory")
    @NotNull(message = "Password is mandatory")
    @Size(min = 8, message = "Password should be at least 8 characters long")
    private String password;
}
