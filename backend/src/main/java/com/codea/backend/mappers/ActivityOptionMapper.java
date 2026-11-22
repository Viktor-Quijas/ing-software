package com.codea.backend.mappers;

import com.codea.backend.dtos.ActivityOptionDto;
import com.codea.backend.entitys.*;
import org.springframework.stereotype.Component;

@Component
public class ActivityOptionMapper {

    public ActivityOptionDto toDto(ActivityOption e) {
        if (e == null) return null;
        return ActivityOptionDto.builder()
                .idActivityOption(e.getIdActivityOption())
                .textOption(e.getTextOption())
                .isCorrect(e.getIsCorrect())
                .orderOption(e.getOrderOption())
                .idActivityQuestion(e.getActivityQuestion() == null ? null : e.getActivityQuestion().getIdActivityQuestion())
                .build();
    }

    public ActivityOption toEntity(ActivityOptionDto d) {
        if (d == null) return null;
        ActivityOption e = new ActivityOption();
        e.setIdActivityOption(d.getIdActivityOption());
        updateEntity(e, d);
        return e;
    }

    /** Copia los campos del DTO (sin tocar el id). Las llaves foráneas se enlazan por id. */
    public void updateEntity(ActivityOption e, ActivityOptionDto d) {
        e.setTextOption(d.getTextOption());
        e.setIsCorrect(d.getIsCorrect());
        e.setOrderOption(d.getOrderOption());
        e.setActivityQuestion(d.getIdActivityQuestion() == null ? null : refActivityQuestion(d.getIdActivityQuestion()));
    }

    private ActivityQuestion refActivityQuestion(Long id) {
        ActivityQuestion t = new ActivityQuestion();
        t.setIdActivityQuestion(id);
        return t;
    }
}
