package com.codea.backend.controllers;

import com.codea.backend.dtos.AuthResponseDto;
import com.codea.backend.dtos.LoginRequestDto;
import com.codea.backend.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    /** Devuelve el token JWT a usar como: Authorization: Bearer <token>. */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(service.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
        @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String header) {
    if (header != null && header.startsWith("Bearer ")) {
        service.logout(header.substring(7));
    }
    return ResponseEntity.noContent().build();
}
}
