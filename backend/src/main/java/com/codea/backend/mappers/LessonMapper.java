package com.codea.backend.mappers;

import com.codea.backend.dtos.LessonDTO;
import com.codea.backend.entitys.*;
import org.springframework.stereotype.Component;

@Component
public class LessonMapper {

    public LessonDTO toDto(Lesson e) {
        if (e == null) return null;
        return LessonDTO.builder()
                .idLesson(e.getIdLesson())
                .titleLesson(e.getTitleLesson())
                .authorLesson(e.getAuthorLesson())
                .contentLesson(e.getContentLesson())
                .lengthLesson(e.getLengthLesson())
                .difficultyLesson(e.getDifficultyLesson())
                .statusLesson(e.getStatusLesson())
                .build();
    }

    public Lesson toEntity(LessonDTO d) {
        if (d == null) return null;
        Lesson e = new Lesson();
        e.setIdLesson(d.getIdLesson());
        updateEntity(e, d);
        return e;
    }

    /** Copia los campos del DTO (sin tocar el id). Las llaves foráneas se enlazan por id. */
    public void updateEntity(Lesson e, LessonDTO d) {
        e.setTitleLesson(d.getTitleLesson());
        e.setAuthorLesson(d.getAuthorLesson());
        e.setContentLesson(d.getContentLesson());
        e.setLengthLesson(d.getLengthLesson());
        e.setDifficultyLesson(d.getDifficultyLesson());
        e.setStatusLesson(d.getStatusLesson());
    }
}
