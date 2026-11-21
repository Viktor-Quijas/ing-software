package com.codea.backend.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityQuestionDto {

    private Long idActivityQuestion;

    @NotBlank
    private String textQuestion;

    private String topicQuestion;

    private String difficultyQuestion;

    private Integer orderQuestion;

    private String typeQuestion;

    @NotNull
    private Long idActivity;
}
