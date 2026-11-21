package com.codea.backend.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityAttemptAnswerDto {

    private Long idAnswer;

    private Boolean isCorrect;

    @NotNull
    private Long idActivityAttempt;

    @NotNull
    private Long idActivityQuestion;

    @NotNull
    private Long idActivityOption;
}
