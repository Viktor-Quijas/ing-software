package com.codea.backend.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosticSubmissionDto {

    @NotNull
    private Long idUser;
    @NotNull
    private Long idExam;
    @NotEmpty
    @Valid
    private List<AnswerSubmissionDto> answers;
}
