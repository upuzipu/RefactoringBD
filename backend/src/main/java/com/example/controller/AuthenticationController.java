package com.example.controller;

import com.example.dto.authentication.AuthenticationRequestDto;
import com.example.dto.authentication.AuthenticationResponseDto;
import com.example.dto.authentication.RegistrationRequestDto;
import com.example.model.entity.User;
import com.example.service.AuthenticationService;
import com.example.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "auth", description = "The controller for registration and authorization")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final JwtService jwtService;

    @PostMapping("/registration")
    @Operation(description = "Register a new user")
    public void register(@RequestBody @Valid RegistrationRequestDto request) {
        User user = new User(request.getPersonEmail(), request.getPersonNickname(), request.getPassword());
        service.register(user);
    }

    @PostMapping("/authentication")
    @Operation(description = "Authorize an existing user")
    public AuthenticationResponseDto authenticate(@RequestBody @Valid AuthenticationRequestDto request) {

        User user = service.authenticate(request.getPersonEmail(), request.getPassword());

        return new AuthenticationResponseDto(jwtService.generateToken(user), user.getPersonEmail());
    }
}
