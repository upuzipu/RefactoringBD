package com.example.service;

import com.example.exception.EntityNotFoundException;
import com.example.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for loading user details.
 * Implements the {@link UserDetailsService} interface for integration with the Spring Security authentication mechanism.
 */
@Service
@Transactional
@AllArgsConstructor
public class DetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Loads user details by their email.
     *
     * @param email the user's email
     * @return a {@link UserDetails} object with the user's data
     * @throws UsernameNotFoundException if no user with the given email is found
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("There is no user with this email address"));
    }

    /**
     * Checks if a user exists with the given login (email).
     *
     * @param login the user's email
     * @return true if the user exists, otherwise false
     */
    public boolean isUserExists(String login) {
        return userRepository.isUserExists(login);
    }

    /**
     * Gets the user ID by their email.
     *
     * @param email the user's email
     * @return the user's ID
     * @throws EntityNotFoundException if no user with the given email is found
     */
    public long getIdByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("There is no user with this email address"))
                .getPersonId();
    }
}
