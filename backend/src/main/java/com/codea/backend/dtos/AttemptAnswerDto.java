package com.codea.backend.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttemptAnswerDto {

    private Long idAnswer;

    private Boolean isCorrect;

    @NotNull
    private Long idAttempt;

    @NotNull
    private Long idQuestion;

    @NotNull
    private Long idOption;
}
