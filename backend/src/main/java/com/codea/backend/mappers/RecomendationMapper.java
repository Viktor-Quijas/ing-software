package com.codea.backend.mappers;

import org.springframework.stereotype.Component;

import com.codea.backend.dtos.RecomendationDTO;
import com.codea.backend.entitys.Activity;
import com.codea.backend.entitys.Algorithm;
import com.codea.backend.entitys.Lesson;
import com.codea.backend.entitys.Recomendation;
import com.codea.backend.entitys.User;

@Component
public class RecomendationMapper {

    public RecomendationDTO toDTO(Recomendation recomendation) {
        return new RecomendationDTO(
                recomendation.getUser().getIdUser(),
                recomendation.getPurposeRecomendation(),
                recomendation.getDateRecomendation(),
                recomendation.getStatusRecomendation());
    }

    public Recomendation toEntity(RecomendationDTO dto, User user, Algorithm algorithm, Lesson lesson, Activity activity) {
        Recomendation recomendation = new Recomendation();
        updateEntity(dto, recomendation, user, algorithm, lesson, activity);
        return recomendation;
    }

    public void updateEntity(RecomendationDTO dto, Recomendation recomendation, User user, Algorithm algorithm, Lesson lesson, Activity activity) {
        recomendation.setUser(user);
        recomendation.setAlgorithm(algorithm);
        recomendation.setLesson(lesson);       // puede ser null
        recomendation.setActivity(activity);   // puede ser null
        recomendation.setPurposeRecomendation(dto.purposeRecomendation());
        recomendation.setDateRecomendation(dto.dateRecomendation());
        recomendation.setStatusRecomendation(dto.statusRecomendation());
    }
}