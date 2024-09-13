package com.collin.booksocial.auth;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationRequest {


    @NotEmpty(message = "Firstname is mandatory")
    @NotBlank(message = "Firstname is mandatory")
    private String firstname;

    @NotEmpty(message = "Lastname is mandatory")
    @NotBlank(message = "Lastname is mandatory")
    private String lastname;

    @Email(message = "Email is not valid")
    @NotEmpty(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Size(min = 8, message = "Password should be at least 8 characters long")
    @NotEmpty(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    private String password;
}
