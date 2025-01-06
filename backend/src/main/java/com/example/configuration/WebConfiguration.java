package com.example.configuration;

import com.example.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web application configuration, including CORS settings, authentication, and password handling.
 */
@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

    private final UserDetailsService userDetailsService;

    /**
     * Configures CORS to allow all HTTP methods and all paths.
     *
     * @param registry the {@link CorsRegistry} object used for CORS configuration.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*"); // Allows all methods for all paths
    }

    /**
     * Configures an authentication provider using {@link DaoAuthenticationProvider}.
     * The provider uses {@link UserDetailsService} and {@link PasswordEncoder}.
     *
     * @return the configured authentication provider {@link AuthenticationProvider}.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Creates an authentication manager based on the current configuration.
     *
     * @param configuration the {@link AuthenticationConfiguration} object.
     * @return the {@link AuthenticationManager} object used for authentication.
     * @throws Exception if an error occurs while retrieving the authentication manager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Configures a password encoder using {@link BCryptPasswordEncoder}.
     *
     * @return the password encoder {@link PasswordEncoder}.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Uses BCrypt for password hashing
    }

    /**
     * Creates a utility for working with JWT (JSON Web Token).
     *
     * @param secret the secret key for signing tokens, retrieved from the application configuration.
     * @return the {@link JwtUtils} object used for working with JWT.
     */
    @Bean
    public JwtUtils jwtUtils(@Value("${jwt.secret}") String secret) {
        return new JwtUtils(secret);
    }
}
