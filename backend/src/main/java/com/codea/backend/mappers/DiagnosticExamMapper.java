package com.codea.backend.mappers;

import com.codea.backend.dtos.DiagnosticExamDto;
import com.codea.backend.entitys.*;
import org.springframework.stereotype.Component;

@Component
public class DiagnosticExamMapper {

    public DiagnosticExamDto toDto(DiagnosticExam e) {
        if (e == null) return null;
        return DiagnosticExamDto.builder()
                .idExam(e.getIdExam())
                .nameExam(e.getNameExam())
                .descriptionExam(e.getDescriptionExam())
                .statusExam(e.getStatusExam())
                .build();
    }

    public DiagnosticExam toEntity(DiagnosticExamDto d) {
        if (d == null) return null;
        DiagnosticExam e = new DiagnosticExam();
        e.setIdExam(d.getIdExam());
        updateEntity(e, d);
        return e;
    }

    /** Copia los campos del DTO (sin tocar el id). Las llaves foráneas se enlazan por id. */
    public void updateEntity(DiagnosticExam e, DiagnosticExamDto d) {
        e.setNameExam(d.getNameExam());
        e.setDescriptionExam(d.getDescriptionExam());
        e.setStatusExam(d.getStatusExam());
    }
}
