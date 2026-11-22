package com.codea.backend.mappers;

import com.codea.backend.dtos.ActivityQuestionDto;
import com.codea.backend.entitys.*;
import org.springframework.stereotype.Component;

@Component
public class ActivityQuestionMapper {

    public ActivityQuestionDto toDto(ActivityQuestion e) {
        if (e == null) return null;
        return ActivityQuestionDto.builder()
                .idActivityQuestion(e.getIdActivityQuestion())
                .textQuestion(e.getTextQuestion())
                .topicQuestion(e.getTopicQuestion())
                .difficultyQuestion(e.getDifficultyQuestion())
                .orderQuestion(e.getOrderQuestion())
                .typeQuestion(e.getTypeQuestion())
                .idActivity(e.getActivity() == null ? null : e.getActivity().getIdActivity())
                .build();
    }

    public ActivityQuestion toEntity(ActivityQuestionDto d) {
        if (d == null) return null;
        ActivityQuestion e = new ActivityQuestion();
        e.setIdActivityQuestion(d.getIdActivityQuestion());
        updateEntity(e, d);
        return e;
    }

    /** Copia los campos del DTO (sin tocar el id). Las llaves foráneas se enlazan por id. */
    public void updateEntity(ActivityQuestion e, ActivityQuestionDto d) {
        e.setTextQuestion(d.getTextQuestion());
        e.setTopicQuestion(d.getTopicQuestion());
        e.setDifficultyQuestion(d.getDifficultyQuestion());
        e.setOrderQuestion(d.getOrderQuestion());
        e.setTypeQuestion(d.getTypeQuestion());
        e.setActivity(d.getIdActivity() == null ? null : refActivity(d.getIdActivity()));
    }

    private Activity refActivity(Long id) {
        Activity t = new Activity();
        t.setIdActivity(id);
        return t;
    }
}
