package com.codea.backend.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityOptionDto {

    private Long idActivityOption;

    @NotBlank
    private String textOption;

    private Boolean isCorrect;

    private Integer orderOption;

    @NotNull
    private Long idActivityQuestion;
}
