package com.codea.backend.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamDetailDto {

    private Long idExam;
    private String nameExam;
    private String descriptionExam;
    private String statusExam;
    private List<QuestionDetailDto> questions;
}
