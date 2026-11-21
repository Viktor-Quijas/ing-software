package com.codea.backend.dtos;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityAttemptDto {

    private Long idActivityAttempt;

    private Double scoreAttempt;

    private LocalDate dateAttempt;

    private String statusAttempt;

    @NotNull
    private Long idUser;

    @NotNull
    private Long idActivity;
}
