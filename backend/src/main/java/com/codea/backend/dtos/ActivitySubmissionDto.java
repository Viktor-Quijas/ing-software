package com.codea.backend.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivitySubmissionDto {

    @NotNull
    private Long idUser;
    @NotNull
    private Long idActivity;
    @NotEmpty
    @Valid
    private List<AnswerSubmissionDto> answers;
}
