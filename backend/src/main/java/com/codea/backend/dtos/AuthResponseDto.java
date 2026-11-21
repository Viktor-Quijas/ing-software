package com.codea.backend.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDto {

    private String token;
    /** Siempre "Bearer": se manda en el header Authorization: Bearer <token>. */
    private String tokenType;
    /** Segundos hasta que expira el token. */
    private long expiresIn;
    private UserResponseDto user;
}
