package com.codea.backend.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonDTO {

    private Long idLesson;

    @NotBlank
    private String titleLesson;

    private String authorLesson;

    private String contentLesson;

    private Integer lengthLesson;

    private String difficultyLesson;

    private String statusLesson;
}
