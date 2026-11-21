package com.codea.backend.dtos;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecomendationDto {

    private Long idRecomendation;

    private String purposeRecomendation;

    private String descriptionRecomendation;

    private LocalDate dateRecomendation;

    private String statusRecomendation;

    @NotNull
    private Long idUser;

    private Long idAlgorithm;

    @NotNull
    private Long idLesson;

    private Long idActivity;
}
