package com.codea.backend.mappers;

import org.springframework.stereotype.Component;

import com.codea.backend.dtos.AlgorithmDTO;
import com.codea.backend.entitys.Algorithm;

@Component
public class AlgorithmMapper {

    public AlgorithmDTO toDTO(Algorithm algorithm) {
        return new AlgorithmDTO(
                algorithm.getNameAlgorithm(),
                algorithm.getDescriptionAlgorithm(),
                algorithm.getVersionAlgorithm(),
                algorithm.getStatusAlgorithm());
    }

    public Algorithm toEntity(AlgorithmDTO dto) {
        Algorithm algorithm = new Algorithm();
        updateEntity(dto, algorithm);
        return algorithm;
    }

    public void updateEntity(AlgorithmDTO dto, Algorithm algorithm) {
        algorithm.setNameAlgorithm(dto.nameAlgorithm());
        algorithm.setDescriptionAlgorithm(dto.descriptionAlgorithm());
        algorithm.setVersionAlgorithm(dto.versionAlgorithm());
        algorithm.setStatusAlgorithm(dto.statusAlgorithm());
    }
}