package com.codea.backend.mappers;

import org.springframework.stereotype.Component;

import com.codea.backend.dtos.StyleLearningDTO;
import com.codea.backend.entitys.StyleLearning;

@Component
public class StyleLearningMapper {

    public StyleLearningDTO toDTO(StyleLearning style) {
        return new StyleLearningDTO(
                style.getIdStyle(),
                style.getNameStyle(),
                style.getDescriptionStyle());
    }

    public StyleLearning toEntity(StyleLearningDTO dto) {
        StyleLearning style = new StyleLearning();
        updateEntity(dto, style);
        return style;
    }

    public void updateEntity(StyleLearningDTO dto, StyleLearning style) {
        style.setNameStyle(dto.nameStyle());
        style.setDescriptionStyle(dto.descriptionStyle());
    }
}