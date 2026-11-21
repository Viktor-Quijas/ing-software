package com.codea.backend.mappers;

import com.codea.backend.dtos.ActivityAttemptDto;
import com.codea.backend.entitys.*;
import org.springframework.stereotype.Component;

@Component
public class ActivityAttemptMapper {

    public ActivityAttemptDto toDto(ActivityAttempt e) {
        if (e == null) return null;
        return ActivityAttemptDto.builder()
                .idActivityAttempt(e.getIdActivityAttempt())
                .scoreAttempt(e.getScoreAttempt())
                .dateAttempt(e.getDateAttempt())
                .statusAttempt(e.getStatusAttempt())
                .idUser(e.getUser() == null ? null : e.getUser().getIdUser())
                .idActivity(e.getActivity() == null ? null : e.getActivity().getIdActivity())
                .build();
    }

    public ActivityAttempt toEntity(ActivityAttemptDto d) {
        if (d == null) return null;
        ActivityAttempt e = new ActivityAttempt();
        e.setIdActivityAttempt(d.getIdActivityAttempt());
        updateEntity(e, d);
        return e;
    }

    /** Copia los campos del DTO (sin tocar el id). Las llaves foráneas se enlazan por id. */
    public void updateEntity(ActivityAttempt e, ActivityAttemptDto d) {
        e.setScoreAttempt(d.getScoreAttempt());
        e.setDateAttempt(d.getDateAttempt());
        e.setStatusAttempt(d.getStatusAttempt());
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
