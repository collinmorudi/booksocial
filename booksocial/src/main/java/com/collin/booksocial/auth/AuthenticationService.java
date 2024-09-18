package com.collin.booksocial.auth;

import com.collin.booksocial.email.EmailTemplateName;
import com.collin.booksocial.user.Token;
import com.collin.booksocial.user.User;
import com.collin.booksocial.email.EmailService;
import com.collin.booksocial.repository.RoleRepository;
import com.collin.booksocial.repository.TokenRepository;
import com.collin.booksocial.repository.UserRepository;
import com.collin.booksocial.security.JwtService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * AuthenticationService handles user registration, authentication, and account activation.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    /**
     * Repository interface for performing CRUD operations on User entities.
     * This repository is mainly used within the AuthenticationService to manage user data.
     */
    private final UserRepository userRepository;
    /**
     * The PasswordEncoder instance used for encoding and matching passwords
     * within the authentication process.
     */
    private final PasswordEncoder passwordEncoder;
    /**
     * Service responsible for generating, validating, and managing JWTs.
     */
    private final JwtService jwtService;
    /**
     * Repository for managing Role entities. Used to perform CRUD operations
     * and queries related to roles within the application authentication context.
     */
    private final RoleRepository roleRepository;
    /**
     * Service responsible for handling email functionalities such as sending validation and activation emails.
     */
    private final EmailService emailService;
    /**
     * Repository interface for handling CRUD operations on Token entities.
     * This repository is essential for managing user activation tokens,
     * which includes storing, retrieving, updating, and deleting token records.
     */
    private final TokenRepository tokenRepository;
    /**
     * Manages the authentication process, including the validation of user credentials and
     * the generation of authentication tokens.
     */
    private final AuthenticationManager authenticationManager;
    /**
     * The URL used for user account activation.
     * This URL is sent to the user's email to activate their account after registration.
     * It is loaded from the application properties using the key 'application.mailing.frontend.activation-url'.
     */
    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    /**
     * Registers a new user with the given registration request details.
     *
     * This method creates a new user with the role "USER", encodes their password,
     * and sets their account to locked and not yet enabled. It saves the new user to
     * the database and sends a validation email.
     *
     * @param request the registration request containing user details such as firstname,
     *                lastname, email, and password.
     * @throws MessagingException if there is an error while sending the validation email.
     */
    public void register(RegistrationRequest request) throws MessagingException {
        var userRole = roleRepository.findByName("USER")
                // todo - better exception handling
                .orElseThrow(() -> new IllegalStateException("ROLE USER was not initiated"));
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        sendValidationEmail(user);
    }


    /**
     * Generates an activation token for the specified user and saves it in the database.
     *
     * @param user The user for whom the activation token is generated.
     * @return The generated activation token.
     */
    private String generateAndSaveActivationToken(User user) {
        // Generate a token
        String generatedToken = generateActivationCode();

        try {
            var token = Token.builder()
                    .token(generatedToken)
                    .createdAt(LocalDateTime.now())
                    .expiresAt(LocalDateTime.now().plusMinutes(5))
                    .user(user)
                    .build();
            tokenRepository.save(token);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return generatedToken;
    }

    /**
     * Generates a 6-digit activation code using numeric characters (0-9).
     *
     * @return a randomly generated 6-digit activation code as a String
     */
    private String generateActivationCode() {

        String characters = "0123456789";

        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 6; i++) {
            int randomInt = random.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomInt));
        }
        return codeBuilder.toString();
    }

    /**
     * Sends a validation email to the user with an activation token.
     *
     * @param user the user to whom the validation email will be sent
     * @throws MessagingException if there is an error while sending the email
     */
    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);

        emailService.sendEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );
    }

    /**
     * Authenticates a user based on the provided credentials in the request.
     *
     * @param request The authentication request containing user credentials.
     * @return An AuthenticationResponse containing the JWT token if authentication is successful.
     */
    public AuthenticationResponse authenticate(@Valid AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullName", user.fullName());
        var jwtToken = jwtService.generateToken(claims, user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    /**
     * Activates a user's account using the provided activation token.
     *
     * @param token the activation token provided to the user
     * @throws Throwable if any error occurs during account activation
     */
    //    @Transactional
    public void activateAccount(String token) throws Throwable {
        Token savedToken = (Token) tokenRepository.findByToken(token)
                // todo exception has to be defined
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw  new RuntimeException("Activation token has expired. A new token has been sent to the same email address");
        }
        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }
}