package com.example.service;

import com.example.exception.EntityNotFoundException;
import com.example.model.entity.User;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for user authentication and registration.
 * Handles new user registration and authentication of existing users.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Registers a new user.
     * The user's password is encoded before saving.
     *
     * @param user the user object that should be registered
     */
    public void register(User user) {
        // Encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the new user to the database
        userRepository.createUser(user.getUsername(), user.getPersonEmail(), user.getPassword());
    }

    /**
     * Authenticates the user by their email and password.
     *
     * @param userEmail the user's email
     * @param password the user's password
     * @return the {@link User} object with the authenticated user's data
     * @throws EntityNotFoundException if no user with the given email is found
     */
    public User authenticate(String userEmail, String password) {
        // Authenticate the user credentials using AuthenticationManager
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userEmail, password)
        );

        // Retrieve the user by email or throw an exception if the user is not found
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("There is no user with this email address"));
    }
}
