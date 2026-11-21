package com.codea.backend.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StyleLearningDto {

    private Long idStyle;

    @NotBlank
    private String nameStyle;

    private String descriptionStyle;
}
