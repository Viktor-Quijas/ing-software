package com.codea.backend.controllers;

import com.codea.backend.dtos.AuthResponseDto;
import com.codea.backend.dtos.LoginRequestDto;
import com.codea.backend.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
