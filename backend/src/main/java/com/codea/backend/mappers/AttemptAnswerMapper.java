package com.codea.backend.mappers;

import com.codea.backend.dtos.AttemptAnswerDto;
import com.codea.backend.entitys.*;
import org.springframework.stereotype.Component;

@Component
public class AttemptAnswerMapper {

    public AttemptAnswerDto toDto(AttemptAnswer e) {
        if (e == null) return null;
        return AttemptAnswerDto.builder()
                .idAnswer(e.getIdAnswer())
                .isCorrect(e.getIsCorrect())
                .idAttempt(e.getAttempt() == null ? null : e.getAttempt().getIdAttempt())
                .idQuestion(e.getQuestion() == null ? null : e.getQuestion().getIdQuestion())
                .idOption(e.getOption() == null ? null : e.getOption().getIdOption())
                .build();
    }

    public AttemptAnswer toEntity(AttemptAnswerDto d) {
        if (d == null) return null;
        AttemptAnswer e = new AttemptAnswer();
        e.setIdAnswer(d.getIdAnswer());
        updateEntity(e, d);
        return e;
    }

    /** Copia los campos del DTO (sin tocar el id). Las llaves foráneas se enlazan por id. */
    public void updateEntity(AttemptAnswer e, AttemptAnswerDto d) {
        e.setIsCorrect(d.getIsCorrect());
        e.setAttempt(d.getIdAttempt() == null ? null : refDiagnosticAttempt(d.getIdAttempt()));
        e.setQuestion(d.getIdQuestion() == null ? null : refQuestion(d.getIdQuestion()));
        e.setOption(d.getIdOption() == null ? null : refOption(d.getIdOption()));
    }

    private DiagnosticAttempt refDiagnosticAttempt(Long id) {
        DiagnosticAttempt t = new DiagnosticAttempt();
        t.setIdAttempt(id);
        return t;
    }

    private Question refQuestion(Long id) {
        Question t = new Question();
        t.setIdQuestion(id);
        return t;
    }

    private Option refOption(Long id) {
        Option t = new Option();
        t.setIdOption(id);
        return t;
    }
}
