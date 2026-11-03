package com.codea.backend.dtos;

public record UserDTO(
        Long idUser,
        String nameUser,
        String lastnameUser,
        String emailUser,
        String rolUser,
        String statusUser,
        Long idAlgorithm,
        Long idStyle) 
        {
}