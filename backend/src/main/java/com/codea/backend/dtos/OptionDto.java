package com.codea.backend.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OptionDto {

    private Long idOption;

    @NotBlank
    private String textOption;

    private Boolean isCorrect;

    @NotNull
    private Long idQuestion;
}
