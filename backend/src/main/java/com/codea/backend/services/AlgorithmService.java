package com.codea.backend.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.codea.backend.dtos.AlgorithmDTO;
import com.codea.backend.entitys.Algorithm;
import com.codea.backend.mappers.AlgorithmMapper;
import com.codea.backend.repositorys.AlgorithmRepository;

@Service
public class AlgorithmService {

    private final AlgorithmRepository algorithmRepository;
    private final AlgorithmMapper algorithmMapper;

    public AlgorithmService(AlgorithmRepository algorithmRepository, AlgorithmMapper algorithmMapper) {
        this.algorithmRepository = algorithmRepository;
        this.algorithmMapper = algorithmMapper;
    }

    public List<AlgorithmDTO> findAll() {
        return algorithmRepository.findAll().stream().map(algorithmMapper::toDTO).toList();
    }

    public AlgorithmDTO findById(Long id) {
        return algorithmMapper.toDTO(getAlgorithm(id));
    }

    public AlgorithmDTO create(AlgorithmDTO dto) {
        return algorithmMapper.toDTO(algorithmRepository.save(algorithmMapper.toEntity(dto)));
    }

    public AlgorithmDTO update(Long id, AlgorithmDTO dto) {
        Algorithm algorithm = getAlgorithm(id);
        algorithmMapper.updateEntity(dto, algorithm);
        return algorithmMapper.toDTO(algorithmRepository.save(algorithm));
    }

    public void delete(Long id) {
        getAlgorithm(id);
        algorithmRepository.deleteById(id);
    }

    private Algorithm getAlgorithm(Long id) {
        return algorithmRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Algoritmo no encontrado con id " + id));
    }
}