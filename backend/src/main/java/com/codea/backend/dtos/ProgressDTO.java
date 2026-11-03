package com.codea.backend.dtos;

import java.time.LocalDate;

public record ProgressDTO(
    Long idProgress,
    String statusProgress,
    Double gradeProgress,
    Integer triesProgress, 
    LocalDate dateProgress

) {

}

