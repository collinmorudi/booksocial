package com.collin.booksocial.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class that defines beans for authentication and auditing.
 */
@Configuration
@RequiredArgsConstructor
public class BeansConfig {

    /**
     * UserDetailsService instance for retrieving user-related data for authentication processes.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Creates and configures a {@code DaoAuthenticationProvider} bean.
     * This provider uses a user details service and password encoder
     * for authentication purposes.
     *
     * @return an {@code AuthenticationProvider} instance configured with
     *         a user details service and password encoder.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider  authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Configures the authentication manager for the application.
     *
     * @param config the configuration object for authentication.
     * @return an instance of AuthenticationManager.
     * @throws Exception if there is an error obtaining the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Provides an implementation of AuditorAware for obtaining the current auditor.
     *
     * @return an instance of AuditorAware<Integer> that supplies the current auditor's identifier
     */
    @Bean
    public AuditorAware<Integer> auditorAware() {
        return new ApplicationAuditAware();
    }

    /**
     * Bean definition for PasswordEncoder.
     *
     * @return a BCryptPasswordEncoder instance to be used for hashing passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
