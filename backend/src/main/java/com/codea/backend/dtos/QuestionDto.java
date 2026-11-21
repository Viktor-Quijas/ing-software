package com.codea.backend.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDto {

    private Long idQuestion;

    @NotBlank
    private String textQuestion;

    private String topicQuestion;

    private String difficultyQuestion;

    @NotNull
    private Long idExam;
}
