package com.codea.backend.mappers;

import com.codea.backend.dtos.StyleLearningDto;
import com.codea.backend.entitys.*;
import org.springframework.stereotype.Component;

@Component
public class StyleLearningMapper {

    public StyleLearningDto toDto(StyleLearning e) {
        if (e == null) return null;
        return StyleLearningDto.builder()
                .idStyle(e.getIdStyle())
                .nameStyle(e.getNameStyle())
                .descriptionStyle(e.getDescriptionStyle())
                .build();
    }

    public StyleLearning toEntity(StyleLearningDto d) {
        if (d == null) return null;
        StyleLearning e = new StyleLearning();
        e.setIdStyle(d.getIdStyle());
        updateEntity(e, d);
        return e;
    }

    /** Copia los campos del DTO (sin tocar el id). Las llaves foráneas se enlazan por id. */
    public void updateEntity(StyleLearning e, StyleLearningDto d) {
        e.setNameStyle(d.getNameStyle());
        e.setDescriptionStyle(d.getDescriptionStyle());
    }
}
