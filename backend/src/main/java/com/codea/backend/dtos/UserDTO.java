package com.codea.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDTO(
        Long idUser,
        String nameUser,
        String lastnameUser,
        String emailUser,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) String passwordUser,
        String rolUser,
        String statusUser,
        Long idAlgorithm,
        Long idStyle) {
}