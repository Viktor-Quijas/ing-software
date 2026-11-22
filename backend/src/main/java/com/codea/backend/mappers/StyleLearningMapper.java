package com.codea.backend.mappers;

import com.codea.backend.dtos.StyleLearningDTO;
import com.codea.backend.entitys.*;
import org.springframework.stereotype.Component;

@Component
public class StyleLearningMapper {

    public StyleLearningDTO toDto(StyleLearning e) {
        if (e == null) return null;
        return StyleLearningDTO.builder()
                .idStyle(e.getIdStyle())
                .nameStyle(e.getNameStyle())
                .descriptionStyle(e.getDescriptionStyle())
                .build();
    }

    public StyleLearning toEntity(StyleLearningDTO d) {
        if (d == null) return null;
        StyleLearning e = new StyleLearning();
        e.setIdStyle(d.getIdStyle());
        updateEntity(e, d);
        return e;
    }

    /** Copia los campos del DTO (sin tocar el id). Las llaves foráneas se enlazan por id. */
    public void updateEntity(StyleLearning e, StyleLearningDTO d) {
        e.setNameStyle(d.getNameStyle());
        e.setDescriptionStyle(d.getDescriptionStyle());
    }
}
