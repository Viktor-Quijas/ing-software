package com.codea.backend.dtos;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosticAttemptDto {

    private Long idAttempt;

    private Double scoreAttempt;

    private LocalDate dateAttempt;

    @NotNull
    private Long idUser;

    @NotNull
    private Long idExam;
}
