package com.example.service;

import com.example.model.entity.User;
import com.example.utils.JwtUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Service for working with JWT (JSON Web Token).
 * Responsible for generating tokens for users.
 */
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtUtils jwtUtils;

    /**
     * Generates a JWT token for a user with empty additional claims.
     *
     * @param user the {@link User} object for which the token is generated
     * @return a string representing the generated token
     */
    public String generateToken(User user) {
        return generateToken(new HashMap<>(), user);
    }

    /**
     * Generates a JWT token for a user with additional claims.
     *
     * @param extraClaims additional data to be included in the token
     * @param user the {@link User} object for which the token is generated
     * @return a string representing the generated token
     */
    public String generateToken(Map<String, Object> extraClaims, User user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)  // Set additional data in the token
                .setSubject(user.getUsername())  // Set the username as the subject
                .setIssuedAt(new Date(System.currentTimeMillis()))  // Set the issue date of the token
                .signWith(jwtUtils.getSignInKey(), SignatureAlgorithm.HS256)  // Sign the token with the key
                .compact();  // Compact the token into a string
    }
}
