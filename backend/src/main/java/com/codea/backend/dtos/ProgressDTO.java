package com.codea.backend.dtos;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgressDTO {

    private Long idProgress;

    private String statusProgress;

    private Double gradeProgress;

    private Integer triesProgress;

    private LocalDate dateProgress;

    @NotNull
    private Long idUser;

    @NotNull
    private Long idActivity;
}
