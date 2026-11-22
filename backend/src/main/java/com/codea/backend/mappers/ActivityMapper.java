package com.codea.backend.mappers;

import com.codea.backend.dtos.ActivityDTO;
import com.codea.backend.entitys.*;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper {

    public ActivityDTO toDto(Activity e) {
        if (e == null) return null;
        return ActivityDTO.builder()
                .idActivity(e.getIdActivity())
                .titleActivity(e.getTitleActivity())
                .descriptionActivity(e.getDescriptionActivity())
                .typeActivity(e.getTypeActivity())
                .instructionActivity(e.getInstructionActivity())
                .lengthActivity(e.getLengthActivity())
                .passingScore(e.getPassingScore())
                .idLesson(e.getLesson() == null ? null : e.getLesson().getIdLesson())
                .build();
    }

    public Activity toEntity(ActivityDTO d) {
        if (d == null) return null;
        Activity e = new Activity();
        e.setIdActivity(d.getIdActivity());
        updateEntity(e, d);
        return e;
    }

    /** Copia los campos del DTO (sin tocar el id). Las llaves foráneas se enlazan por id. */
    public void updateEntity(Activity e, ActivityDTO d) {
        e.setTitleActivity(d.getTitleActivity());
        e.setDescriptionActivity(d.getDescriptionActivity());
        e.setTypeActivity(d.getTypeActivity());
        e.setInstructionActivity(d.getInstructionActivity());
        e.setLengthActivity(d.getLengthActivity());
        e.setPassingScore(d.getPassingScore());
        e.setLesson(d.getIdLesson() == null ? null : refLesson(d.getIdLesson()));
    }

    private Lesson refLesson(Long id) {
        Lesson t = new Lesson();
        t.setIdLesson(id);
        return t;
    }
}
