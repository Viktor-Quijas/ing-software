package com.codea.backend.dtos;

import lombok.*;

/** Nunca expone la contraseña. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {

    private Long idUser;
    private String nameUser;
    private String lastnameUser;
    private String emailUser;
    private String rolUser;
    private String statusUser;
    private Long idAlgorithm;
    private Long idStyle;
    private String styleName;
}
