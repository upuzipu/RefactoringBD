package com.example.model.enumeration;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * Enumeration representing user roles in the system.
 * Implements the {@link GrantedAuthority} interface, allowing its use in security contexts.
 */
@RequiredArgsConstructor
public enum Role implements GrantedAuthority {

    /**
     * User role with limited access rights.
     */
    USER("ROLE_USER"),

    /**
     * Admin role with extended access rights.
     */
    ADMIN("ROLE_ADMIN");

    private final String value;

    /**
     * Get the string representation of the role.
     * @return the string value of the role
     */
    @Override
    public String getAuthority() {
        return value;
    }
}
