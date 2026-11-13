package com.healthcare.authservice.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.authservice.config.CustomAuthenticationProvider;
import com.healthcare.authservice.dto.LoginRequest;
import com.healthcare.authservice.service.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final CustomAuthenticationProvider authenticationManager;
    private final JwtService jwtService;

    public AuthController(
    		CustomAuthenticationProvider authenticationManager,
            JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                    )
                );

        String token = jwtService.generateToken(
                authentication.getName()
        );

        return ResponseEntity.ok(
                Map.of("accessToken", token)
        );
    }
}
