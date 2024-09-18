package com.collin.booksocial.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * Configuration class for setting up OpenAPI documentation in a Spring application.
 *
 * This class uses annotations to define the OpenAPI specification details, including API information,
 * server details, and security schemes.
 *
 * The API information includes the contact details, description, title, version, license, and terms of service.
 * Two server environments are specified: Local ENV and PROD ENV.
 * A JWT-based security scheme is defined with bearer authentication.
 */
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Collin",
                        email = "collinmorudi@gmail.com",
                        url = "https://github.com/collinmorudi"
                ),
                description = "OpenApi documentation for Spring Security",
                title = "OpenApi specification - Collin Morudi",
                version = "1.0",
                license = @License(
                        name = "To be added",
                        url = "https://tobeadded"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8088/api/v1"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "https://tobeadded"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description is going to be added",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        flows = @OAuthFlows(
                clientCredentials =
                @OAuthFlow(
                        authorizationUrl = "http://localhost:9090/realms/book-social-network/protocol/openid-connect/auth"
                )
        ),
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
