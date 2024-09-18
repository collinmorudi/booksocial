package com.collin.booksocial.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service for managing JSON Web Tokens (JWT).
 * Provides functionality to generate, validate and extract information from JWT tokens.
 */
@Service
public class JwtService {

    /**
     * The secret key used for signing and verifying JSON Web Tokens (JWT).
     * This key is retrieved from the configuration property 'application.security.jwt.secret-key'.
     */
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    /**
     * Represents the expiration time in milliseconds for the JSON Web Token (JWT).
     * This value is injected from the application configuration property `application.security.jwt.expiration`.
     */
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    /**
     * Extracts the username from the given JWT token.
     *
     * @param token the JWT token from which the username is to be extracted
     * @return the username extracted from the token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from a JWT token using the provided claims resolver function.
     *
     * @param token the JWT token to extract the claim from.
     * @param claimsResolver a function to apply to the extracted claims to retrieve the desired claim.
     * @return the claim extracted from the token.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a JSON Web Token (JWT) for the given user details.
     *
     * @param userDetails the user details for which the token is to be generated
     * @return the generated JWT as a string
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token using the provided extra claims and user details.
     *
     * @param extraClaims A map containing additional claims to be included in the token.
     * @param userDetails The user details object containing user-specific information.
     * @return A JWT token as a string.
     */
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    /**
     * Builds a JSON Web Token (JWT) with the specified claims, user details, and expiration time.
     *
     * @param extraClaims Additional claims to include in the token.
     * @param userDetails Information about the user for whom the token is being created.
     * @param expiration The expiration time of the token in milliseconds.
     * @return A string representing the generated JWT.
     */
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        var authorities = userDetails.getAuthorities()
                .stream().
                map(GrantedAuthority::getAuthority)
                .toList();
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .claim("authorities", authorities)
                .signWith(getSignInKey())
                .compact();
    }

    /**
     * Validates the provided JWT token against user details.
     *
     * @param token the JWT token to be validated
     * @param userDetails the user details to validate the token against
     * @return true if the token is valid for the given user details and not expired, false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Checks if the given JWT token is expired.
     *
     * @param token the JWT token to check
     * @return true if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the provided JWT token.
     *
     * @param token the JWT token from which to extract the expiration date.
     * @return the expiration date of the provided token.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts all claims from a given JWT token.
     *
     * @param token the JWT token from which claims are to be extracted
     * @return the claims extracted from the token
     */
    private Claims extractAllClaims(String token) {
        return Jwts
//                .parserBuilder()
                .parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Decodes the secret key from its Base64 encoded form and generates a cryptographic key
     * for HMAC SHA signature.
     *
     * @return the cryptographic key used to sign and verify JWT tokens.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
