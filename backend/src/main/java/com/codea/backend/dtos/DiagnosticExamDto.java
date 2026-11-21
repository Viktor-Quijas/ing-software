package com.codea.backend.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosticExamDto {

    private Long idExam;

    @NotBlank
    private String nameExam;

    private String descriptionExam;

    private String statusExam;
}
