package com.example.filter;

import com.example.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;  // Utility for extracting and validating JWT
    private final UserDetailsService userDetailsService;  // Service to load user details

    /**
     * Method that is called for each incoming HTTP request to check the JWT and authenticate the user.
     *
     * @param request HTTP request
     * @param response HTTP response
     * @param filterChain Filter chain to continue the request processing
     * @throws ServletException If an error occurs during request processing
     * @throws IOException If an input/output error occurs during request processing
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Extract the Authorization header
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // If the Authorization header is missing or does not start with "Bearer ", continue with the filter chain
        if (Objects.isNull(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT token from the header (remove the "Bearer " prefix)
        jwt = authHeader.substring(7);
        // Extract the user's email from the JWT
        userEmail = jwtUtils.extractEmail(jwt);

        // If the email is not null and the user is not authenticated
        if (!Objects.isNull(userEmail) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            // Load user details from the database or other source
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // If the token is valid, authenticate the user
            if (jwtUtils.isTokenValid(jwt, userDetails)) {
                // Create an authentication token for the user
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,  // No password information
                        userDetails.getAuthorities()  // User's roles and permissions
                );

                // Set the authentication details (e.g., request details)
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the security context with the user's authentication
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
