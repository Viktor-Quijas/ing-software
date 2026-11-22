package com.codea.backend.mappers;

import com.codea.backend.dtos.QuestionDto;
import com.codea.backend.entitys.*;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {

    public QuestionDto toDto(Question e) {
        if (e == null) return null;
        return QuestionDto.builder()
                .idQuestion(e.getIdQuestion())
                .textQuestion(e.getTextQuestion())
                .topicQuestion(e.getTopicQuestion())
                .difficultyQuestion(e.getDifficultyQuestion())
                .idExam(e.getExam() == null ? null : e.getExam().getIdExam())
                .build();
    }

    public Question toEntity(QuestionDto d) {
        if (d == null) return null;
        Question e = new Question();
        e.setIdQuestion(d.getIdQuestion());
        updateEntity(e, d);
        return e;
    }

    /** Copia los campos del DTO (sin tocar el id). Las llaves foráneas se enlazan por id. */
    public void updateEntity(Question e, QuestionDto d) {
        e.setTextQuestion(d.getTextQuestion());
        e.setTopicQuestion(d.getTopicQuestion());
        e.setDifficultyQuestion(d.getDifficultyQuestion());
        e.setExam(d.getIdExam() == null ? null : refDiagnosticExam(d.getIdExam()));
    }

    private DiagnosticExam refDiagnosticExam(Long id) {
        DiagnosticExam t = new DiagnosticExam();
        t.setIdExam(id);
        return t;
    }
}
