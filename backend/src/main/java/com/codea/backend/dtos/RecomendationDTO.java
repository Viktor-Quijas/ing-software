package com.codea.backend.dtos;

import java.time.LocalDate;

public record RecomendationDTO( 
    long idUser, 
    String purposeRecomendation,
    LocalDate dateRecomendation,
    String statusRecomendation)
{
}
