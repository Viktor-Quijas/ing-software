package com.codea.backend.mappers;

import com.codea.backend.dtos.AlgorithmDto;
import com.codea.backend.entitys.*;
import org.springframework.stereotype.Component;

@Component
public class AlgorithmMapper {

    public AlgorithmDto toDto(Algorithm e) {
        if (e == null) return null;
        return AlgorithmDto.builder()
                .idAlgorithm(e.getIdAlgorithm())
                .nameAlgorithm(e.getNameAlgorithm())
                .descriptionAlgorithm(e.getDescriptionAlgorithm())
                .versionAlgorithm(e.getVersionAlgorithm())
                .statusAlgorithm(e.getStatusAlgorithm())
                .build();
    }

    public Algorithm toEntity(AlgorithmDto d) {
        if (d == null) return null;
        Algorithm e = new Algorithm();
        e.setIdAlgorithm(d.getIdAlgorithm());
        updateEntity(e, d);
        return e;
    }

    /** Copia los campos del DTO (sin tocar el id). Las llaves foráneas se enlazan por id. */
    public void updateEntity(Algorithm e, AlgorithmDto d) {
        e.setNameAlgorithm(d.getNameAlgorithm());
        e.setDescriptionAlgorithm(d.getDescriptionAlgorithm());
        e.setVersionAlgorithm(d.getVersionAlgorithm());
        e.setStatusAlgorithm(d.getStatusAlgorithm());
    }
}
