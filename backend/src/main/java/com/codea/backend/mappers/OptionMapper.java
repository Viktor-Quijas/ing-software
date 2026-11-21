package com.codea.backend.mappers;

import com.codea.backend.dtos.OptionDto;
import com.codea.backend.entitys.*;
import org.springframework.stereotype.Component;

@Component
public class OptionMapper {

    public OptionDto toDto(Option e) {
        if (e == null) return null;
        return OptionDto.builder()
                .idOption(e.getIdOption())
                .textOption(e.getTextOption())
                .isCorrect(e.getIsCorrect())
                .idQuestion(e.getQuestion() == null ? null : e.getQuestion().getIdQuestion())
                .build();
    }

    public Option toEntity(OptionDto d) {
        if (d == null) return null;
        Option e = new Option();
        e.setIdOption(d.getIdOption());
        updateEntity(e, d);
        return e;
    }

    /** Copia los campos del DTO (sin tocar el id). Las llaves foráneas se enlazan por id. */
    public void updateEntity(Option e, OptionDto d) {
        e.setTextOption(d.getTextOption());
        e.setIsCorrect(d.getIsCorrect());
        e.setQuestion(d.getIdQuestion() == null ? null : refQuestion(d.getIdQuestion()));
    }

    private Question refQuestion(Long id) {
        Question t = new Question();
        t.setIdQuestion(id);
        return t;
    }
}
