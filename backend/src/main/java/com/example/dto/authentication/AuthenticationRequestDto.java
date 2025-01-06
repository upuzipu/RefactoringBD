package com.example.dto.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for representing the authentication request data.
 */
@Getter
@Setter
public class AuthenticationRequestDto {

    /**
     * User's email address.
     * Must be a valid email format.
     */
    @Email(message = "Invalid email")
    @JsonProperty("email")
    private String personEmail;

    /**
     * User's password.
     * Must be between 8 to 16 characters and cannot be empty.
     */
    @Size(min = 8, max = 16, message = "Incorrect password size")
    @NotBlank(message = "The password must not be empty")
    private String password;
}
