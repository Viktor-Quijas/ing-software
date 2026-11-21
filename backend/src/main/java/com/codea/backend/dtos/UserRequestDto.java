package com.codea.backend.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto {

    @NotBlank
    private String nameUser;
    private String lastnameUser;
    @NotBlank
    @Email
    private String emailUser;
    /** Obligatoria al crear; al actualizar es opcional (si viene vacía se conserva la actual). */
    private String passwordUser;
    private String rolUser;
    private String statusUser;
    private Long idAlgorithm;
    private Long idStyle;
}
