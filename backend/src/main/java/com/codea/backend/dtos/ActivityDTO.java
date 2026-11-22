package com.codea.backend.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityDto {

    private Long idActivity;

    @NotBlank
    private String titleActivity;

    private String descriptionActivity;

    private String typeActivity;

    private String instructionActivity;

    private Integer lengthActivity;

    private Double passingScore;

    @NotNull
    private Long idLesson;
}
