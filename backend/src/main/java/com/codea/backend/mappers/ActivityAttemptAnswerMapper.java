package com.codea.backend.mappers;

import com.codea.backend.dtos.ActivityAttemptAnswerDto;
import com.codea.backend.entitys.*;
import org.springframework.stereotype.Component;

@Component
public class ActivityAttemptAnswerMapper {

    public ActivityAttemptAnswerDto toDto(ActivityAttemptAnswer e) {
        if (e == null) return null;
        return ActivityAttemptAnswerDto.builder()
                .idAnswer(e.getIdAnswer())
                .isCorrect(e.getIsCorrect())
                .idActivityAttempt(e.getActivityAttempt() == null ? null : e.getActivityAttempt().getIdActivityAttempt())
                .idActivityQuestion(e.getActivityQuestion() == null ? null : e.getActivityQuestion().getIdActivityQuestion())
                .idActivityOption(e.getActivityOption() == null ? null : e.getActivityOption().getIdActivityOption())
                .build();
    }

    public ActivityAttemptAnswer toEntity(ActivityAttemptAnswerDto d) {
        if (d == null) return null;
        ActivityAttemptAnswer e = new ActivityAttemptAnswer();
        e.setIdAnswer(d.getIdAnswer());
        updateEntity(e, d);
        return e;
    }

    /** Copia los campos del DTO (sin tocar el id). Las llaves foráneas se enlazan por id. */
    public void updateEntity(ActivityAttemptAnswer e, ActivityAttemptAnswerDto d) {
        e.setIsCorrect(d.getIsCorrect());
        e.setActivityAttempt(d.getIdActivityAttempt() == null ? null : refActivityAttempt(d.getIdActivityAttempt()));
        e.setActivityQuestion(d.getIdActivityQuestion() == null ? null : refActivityQuestion(d.getIdActivityQuestion()));
        e.setActivityOption(d.getIdActivityOption() == null ? null : refActivityOption(d.getIdActivityOption()));
    }

    private ActivityAttempt refActivityAttempt(Long id) {
        ActivityAttempt t = new ActivityAttempt();
        t.setIdActivityAttempt(id);
        return t;
    }

    private ActivityQuestion refActivityQuestion(Long id) {
        ActivityQuestion t = new ActivityQuestion();
        t.setIdActivityQuestion(id);
        return t;
    }

    private ActivityOption refActivityOption(Long id) {
        ActivityOption t = new ActivityOption();
        t.setIdActivityOption(id);
        return t;
    }
}
