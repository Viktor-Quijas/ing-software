package com.codea.backend.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityDetailDto {

    private Long idActivity;
    private Long idLesson;
    private String titleActivity;
    private String descriptionActivity;
    private String typeActivity;
    private String instructionActivity;
    private Integer lengthActivity;
    private Double passingScore;
    private List<ActivityQuestionDetailDto> questions;
}
