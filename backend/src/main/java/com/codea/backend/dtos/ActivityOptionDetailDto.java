package com.codea.backend.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityOptionDetailDto {

    private Long idActivityOption;
    private String textOption;
    private Integer orderOption;
}
