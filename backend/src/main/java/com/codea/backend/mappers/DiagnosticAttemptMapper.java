package com.codea.backend.mappers;

import com.codea.backend.dtos.DiagnosticAttemptDto;
import com.codea.backend.entitys.*;
import org.springframework.stereotype.Component;

@Component
public class DiagnosticAttemptMapper {

    public DiagnosticAttemptDto toDto(DiagnosticAttempt e) {
        if (e == null) return null;
        return DiagnosticAttemptDto.builder()
                .idAttempt(e.getIdAttempt())
                .scoreAttempt(e.getScoreAttempt())
                .dateAttempt(e.getDateAttempt())
                .idUser(e.getUser() == null ? null : e.getUser().getIdUser())
                .idExam(e.getExam() == null ? null : e.getExam().getIdExam())
                .build();
    }

    public DiagnosticAttempt toEntity(DiagnosticAttemptDto d) {
        if (d == null) return null;
        DiagnosticAttempt e = new DiagnosticAttempt();
        e.setIdAttempt(d.getIdAttempt());
        updateEntity(e, d);
        return e;
    }

    /** Copia los campos del DTO (sin tocar el id). Las llaves foráneas se enlazan por id. */
    public void updateEntity(DiagnosticAttempt e, DiagnosticAttemptDto d) {
        e.setScoreAttempt(d.getScoreAttempt());
        e.setDateAttempt(d.getDateAttempt());
        e.setUser(d.getIdUser() == null ? null : refUser(d.getIdUser()));
        e.setExam(d.getIdExam() == null ? null : refDiagnosticExam(d.getIdExam()));
    }

    private User refUser(Long id) {
        User t = new User();
        t.setIdUser(id);
        return t;
    }

    private DiagnosticExam refDiagnosticExam(Long id) {
        DiagnosticExam t = new DiagnosticExam();
        t.setIdExam(id);
        return t;
    }
}
