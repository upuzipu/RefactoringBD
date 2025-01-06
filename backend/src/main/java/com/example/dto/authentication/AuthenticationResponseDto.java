package com.example.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for representing the response to the user authentication request.
 */
@Getter
@Setter
@AllArgsConstructor
public class AuthenticationResponseDto {

    /**
     * The token received after successful authentication.
     */
    private String token;

    /**
     * User's email address.
     */
    private String email;
}
