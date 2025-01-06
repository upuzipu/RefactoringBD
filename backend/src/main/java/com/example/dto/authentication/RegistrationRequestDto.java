package com.example.dto.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for representing the user registration request data.
 */
@Getter
@Setter
public class RegistrationRequestDto {

    /**
     * User's email address.
     * Must be a valid email address and cannot be empty.
     */
    @Email(message = "Invalid email")
    @JsonProperty("email")
    @NotBlank(message = "The email should not be empty")
    private String personEmail;

    /**
     * User's password.
     * Must be between 8 and 16 characters and cannot be empty.
     */
    @Size(min = 8, max = 16, message = "Incorrect password size")
    @NotBlank(message = "The password must not be empty")
    private String password;

    /**
     * User's nickname.
     * Cannot be empty.
     */
    @JsonProperty("nickname")
    private String personNickname;
}
