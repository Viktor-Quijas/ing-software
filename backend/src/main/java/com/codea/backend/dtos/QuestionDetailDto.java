package com.codea.backend.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDetailDto {

    private Long idQuestion;
    private String textQuestion;
    private String topicQuestion;
    private String difficultyQuestion;
    private List<OptionDetailDto> options;
}
