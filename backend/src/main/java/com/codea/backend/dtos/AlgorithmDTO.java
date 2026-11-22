package com.codea.backend.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlgorithmDTO {

    private Long idAlgorithm;

    @NotBlank
    private String nameAlgorithm;

    private String descriptionAlgorithm;

    private String versionAlgorithm;

    private String statusAlgorithm;
}
