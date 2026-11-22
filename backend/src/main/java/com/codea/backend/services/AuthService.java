package com.codea.backend.services;

import com.codea.backend.dtos.AuthResponseDto;
import com.codea.backend.dtos.LoginRequestDto;
import com.codea.backend.entitys.User;
import com.codea.backend.exceptions.UnauthorizedException;
import com.codea.backend.mappers.UserMapper;
import com.codea.backend.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final TokenBlacklistService tokenBlacklistService;

    @Transactional(readOnly = true)
    public AuthResponseDto login(LoginRequestDto request) {
        User user = userRepository.findByEmailUser(request.getEmailUser())
                .orElseThrow(() -> new UnauthorizedException("Credenciales inválidas"));
        if (!passwordEncoder.matches(request.getPasswordUser(), user.getPasswordUser())) {
            throw new UnauthorizedException("Credenciales inválidas");
        }
        return AuthResponseDto.builder()
                .token(jwtService.generateToken(user))
                .tokenType("Bearer")
                .expiresIn(jwtService.getExpirationSeconds())
                .user(userMapper.toResponse(user))
                .build();
    }

    public void logout(String token) {
    try {
        Claims claims = jwtService.parseClaims(token);
        tokenBlacklistService.revoke(claims.getId(), claims.getExpiration());
    } catch (JwtException | IllegalArgumentException ex) {
    }
}
}
