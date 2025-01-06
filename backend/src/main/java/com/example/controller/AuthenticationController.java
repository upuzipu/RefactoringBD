package com.example.controller;

import com.example.dto.authentication.AuthenticationRequestDto;
import com.example.dto.authentication.AuthenticationResponseDto;
import com.example.dto.authentication.RegistrationRequestDto;
import com.example.model.entity.User;
import com.example.service.AuthenticationService;
import com.example.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing user registration and authentication.
 */
@RestController
@Tag(name = "auth", description = "Controller for user registration and authentication")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final JwtService jwtService;

    /**
     * Registers a new user.
     *
     * @param request a {@link RegistrationRequestDto} object containing registration details.
     */
    @PostMapping("/registration")
    @Operation(summary = "Register a new user", description = "Allows registration of a new user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully registered"),
            @ApiResponse(responseCode = "400", description = "Invalid registration data", content = @Content)
    })
    public void register(@Valid RegistrationRequestDto request) {
        User user = new User(request.getPersonEmail(), request.getPersonNickname(), request.getPassword());
        service.register(user);
    }

    /**
     * Authenticates an existing user.
     *
     * @param request a {@link AuthenticationRequestDto} object containing authentication details.
     * @return an {@link AuthenticationResponseDto} object containing the JWT token and user email.
     */
    @PostMapping("/authentication")
    @Operation(summary = "Authenticate an existing user", description = "Allows user authentication and returns a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully authenticated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid authentication data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content)
    })
    public AuthenticationResponseDto authenticate(@Valid AuthenticationRequestDto request) {
        User user = service.authenticate(request.getPersonEmail(), request.getPassword());
        return new AuthenticationResponseDto(jwtService.generateToken(user), user.getPersonEmail());
    }
}
