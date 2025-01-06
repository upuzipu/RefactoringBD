package com.example.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.function.Function;

/**
 * Utility class for working with JWT (JSON Web Tokens).
 * Provides methods for extracting information from tokens and verifying their validity.
 */
@RequiredArgsConstructor
@Getter
public class JwtUtils {

    private final String secret;

    /**
     * Extracts the user's email from the token.
     *
     * @param token JWT token
     * @return the user's email (subject of the token)
     */
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts an arbitrary claim from the token.
     *
     * @param token JWT token
     * @param claimsResolver function to extract the claim from {@link Claims}
     * @param <T> the type of the claim's value
     * @return the value of the claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token JWT token
     * @return a {@link Claims} object containing all the claims of the token
     */
    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Generates a signing key from the secret.
     *
     * @return the signing key
     */
    public Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Verifies the validity of the token based on the user's email.
     *
     * @param token JWT token
     * @param userDetails user details
     * @return true if the token is valid for the user, otherwise false
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractEmail(token);
        return username.equals(userDetails.getUsername());
    }
}
