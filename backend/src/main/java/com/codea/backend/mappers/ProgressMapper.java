package com.codea.backend.mappers;

import com.codea.backend.dtos.ProgressDto;
import com.codea.backend.entitys.*;
import org.springframework.stereotype.Component;

@Component
public class ProgressMapper {

    public ProgressDto toDto(Progress e) {
        if (e == null) return null;
        return ProgressDto.builder()
                .idProgress(e.getIdProgress())
                .statusProgress(e.getStatusProgress())
                .gradeProgress(e.getGradeProgress())
                .triesProgress(e.getTriesProgress())
                .dateProgress(e.getDateProgress())
                .idUser(e.getUser() == null ? null : e.getUser().getIdUser())
                .idActivity(e.getActivity() == null ? null : e.getActivity().getIdActivity())
                .build();
    }

    public Progress toEntity(ProgressDto d) {
        if (d == null) return null;
        Progress e = new Progress();
        e.setIdProgress(d.getIdProgress());
        updateEntity(e, d);
        return e;
    }

    /** Copia los campos del DTO (sin tocar el id). Las llaves foráneas se enlazan por id. */
    public void updateEntity(Progress e, ProgressDto d) {
        e.setStatusProgress(d.getStatusProgress());
        e.setGradeProgress(d.getGradeProgress());
        e.setTriesProgress(d.getTriesProgress());
        e.setDateProgress(d.getDateProgress());
        e.setUser(d.getIdUser() == null ? null : refUser(d.getIdUser()));
        e.setActivity(d.getIdActivity() == null ? null : refActivity(d.getIdActivity()));
    }

    private User refUser(Long id) {
        User t = new User();
        t.setIdUser(id);
        return t;
    }

    private Activity refActivity(Long id) {
        Activity t = new Activity();
        t.setIdActivity(id);
        return t;
    }
}
