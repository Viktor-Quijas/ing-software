package com.codea.backend.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosticResultDto {

    private Long idAttempt;
    /** +1 por pregunta correcta (sin contar las 3 de estilo). */
    private Double scoreAttempt;
    /** Preguntas que cuentan para el score. */
    private Integer totalQuestions;
    private Long idStyle;
    private String styleName;
    private List<RecomendationDTO> recomendations;
}
