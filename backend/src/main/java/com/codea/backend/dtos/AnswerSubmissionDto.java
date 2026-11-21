package com.codea.backend.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerSubmissionDto {

    /** Examen: id de QUESTION. Actividad: id de ACTIVITY_QUESTION. */
    @NotNull
    private Long idQuestion;

    /** Examen: id de OPTION. Actividad: id de ACTIVITY_OPTION. */
    @NotNull
    private Long idOption;
}
