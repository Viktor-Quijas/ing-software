package com.codea.backend.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityQuestionDetailDto {

    private Long idActivityQuestion;
    private String textQuestion;
    private String topicQuestion;
    private String difficultyQuestion;
    private Integer orderQuestion;
    private String typeQuestion;
    private List<ActivityOptionDetailDto> options;
}
