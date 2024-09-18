package com.collin.booksocial.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * SecurityConfig is a configuration class that sets up the security settings for the application.
 * It enables web security, method security with @Secured annotations, and configures a security filter chain.
 * The filter chain specifies settings such as CORS, CSRF, request authorization, session management, and custom filters.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    /**
     * The authenticationProvider variable is responsible for providing
     * authentication mechanisms to secure the application. It is configured
     * within the SecurityConfig class to integrate custom authentication logic.
     */
    private final AuthenticationProvider authenticationProvider;
    /**
     * The JwtFilter instance responsible for intercepting and processing JWT-based
     * authentication for incoming HTTP requests. This filter ensures that requests
     * have valid JWT tokens and extracts authentication details for further security
     * processing in the application.
     */
    private final JwtFilter jwtAuthFilter;

    /**
     * Configures the security filter chain for the application.
     *
     * @param http the HttpSecurity object to configure security settings
     * @return the configured SecurityFilterChain object
     * @throws Exception if an error occurs while configuring the HttpSecurity object
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( req ->
                        req.requestMatchers(
                                        "/auth/**",
                                        "/v3/api-docs",
                                        "/v3/api-docs/**",
                                        "/swagger-resources",
                                        "/swagger-resources/**",
                                        "/configuration/ui",
                                        "/configuration/security",
                                        "/swagger-ui/**",
                                        "/webjars/**",
                                        "/swagger-ui.html"
                        ).permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
