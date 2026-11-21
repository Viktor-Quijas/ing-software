package com.codea.backend.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityResultDto {

    private Long idActivityAttempt;
    private Double scoreAttempt;
    private Integer totalQuestions;
    private Double passingScore;
    /** true = actividad (y lección) completada; la recomendación se borra. */
    private Boolean passed;
    private String statusAttempt;
}
