package com.codea.backend.mappers;

import com.codea.backend.dtos.AlgorithmDTO;
import com.codea.backend.entitys.*;
import org.springframework.stereotype.Component;

@Component
public class AlgorithmMapper {

    public AlgorithmDTO toDto(Algorithm e) {
        if (e == null) return null;
        return AlgorithmDTO.builder()
                .idAlgorithm(e.getIdAlgorithm())
                .nameAlgorithm(e.getNameAlgorithm())
                .descriptionAlgorithm(e.getDescriptionAlgorithm())
                .versionAlgorithm(e.getVersionAlgorithm())
                .statusAlgorithm(e.getStatusAlgorithm())
                .build();
    }

    public Algorithm toEntity(AlgorithmDTO d) {
        if (d == null) return null;
        Algorithm e = new Algorithm();
        e.setIdAlgorithm(d.getIdAlgorithm());
        updateEntity(e, d);
        return e;
    }

    /** Copia los campos del DTO (sin tocar el id). Las llaves foráneas se enlazan por id. */
    public void updateEntity(Algorithm e, AlgorithmDTO d) {
        e.setNameAlgorithm(d.getNameAlgorithm());
        e.setDescriptionAlgorithm(d.getDescriptionAlgorithm());
        e.setVersionAlgorithm(d.getVersionAlgorithm());
        e.setStatusAlgorithm(d.getStatusAlgorithm());
    }
}
