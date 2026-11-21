package com.codea.backend.mappers;

import com.codea.backend.dtos.RecomendationDto;
import com.codea.backend.entitys.*;
import org.springframework.stereotype.Component;

@Component
public class RecomendationMapper {

    public RecomendationDto toDto(Recomendation e) {
        if (e == null) return null;
        return RecomendationDto.builder()
                .idRecomendation(e.getIdRecomendation())
                .purposeRecomendation(e.getPurposeRecomendation())
                .descriptionRecomendation(e.getDescriptionRecomendation())
                .dateRecomendation(e.getDateRecomendation())
                .statusRecomendation(e.getStatusRecomendation())
                .idUser(e.getUser() == null ? null : e.getUser().getIdUser())
                .idAlgorithm(e.getAlgorithm() == null ? null : e.getAlgorithm().getIdAlgorithm())
                .idLesson(e.getLesson() == null ? null : e.getLesson().getIdLesson())
                .idActivity(e.getActivity() == null ? null : e.getActivity().getIdActivity())
                .build();
    }

    public Recomendation toEntity(RecomendationDto d) {
        if (d == null) return null;
        Recomendation e = new Recomendation();
        e.setIdRecomendation(d.getIdRecomendation());
        updateEntity(e, d);
        return e;
    }

    /** Copia los campos del DTO (sin tocar el id). Las llaves foráneas se enlazan por id. */
    public void updateEntity(Recomendation e, RecomendationDto d) {
        e.setPurposeRecomendation(d.getPurposeRecomendation());
        e.setDescriptionRecomendation(d.getDescriptionRecomendation());
        e.setDateRecomendation(d.getDateRecomendation());
        e.setStatusRecomendation(d.getStatusRecomendation());
        e.setUser(d.getIdUser() == null ? null : refUser(d.getIdUser()));
        e.setAlgorithm(d.getIdAlgorithm() == null ? null : refAlgorithm(d.getIdAlgorithm()));
        e.setLesson(d.getIdLesson() == null ? null : refLesson(d.getIdLesson()));
        e.setActivity(d.getIdActivity() == null ? null : refActivity(d.getIdActivity()));
    }

    private User refUser(Long id) {
        User t = new User();
        t.setIdUser(id);
        return t;
    }

    private Algorithm refAlgorithm(Long id) {
        Algorithm t = new Algorithm();
        t.setIdAlgorithm(id);
        return t;
    }

    private Lesson refLesson(Long id) {
        Lesson t = new Lesson();
        t.setIdLesson(id);
        return t;
    }

    private Activity refActivity(Long id) {
        Activity t = new Activity();
        t.setIdActivity(id);
        return t;
    }
}
