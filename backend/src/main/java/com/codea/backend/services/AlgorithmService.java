package com.codea.backend.services;

import com.codea.backend.dtos.*;
import com.codea.backend.entitys.*;
import com.codea.backend.exceptions.*;
import com.codea.backend.mappers.*;
import com.codea.backend.repositorys.*;
import com.codea.backend.utils.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AlgorithmService {

    private final AlgorithmRepository repository;
    private final AlgorithmMapper mapper;

    @Transactional(readOnly = true)
    public List<AlgorithmDTO> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public AlgorithmDTO findById(Long id) {
        return mapper.toDto(getEntity(id));
    }

    public AlgorithmDTO create(AlgorithmDTO dto) {
        Algorithm entity = mapper.toEntity(dto);
        entity.setIdAlgorithm(null);
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    public AlgorithmDTO update(Long id, AlgorithmDTO dto) {
        Algorithm entity = getEntity(id);
        mapper.updateEntity(entity, dto);
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Algorithm no encontrado: " + id);
        }
        repository.deleteById(id);
        repository.flush();
    }

    @Transactional(readOnly = true)
    public Algorithm getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Algorithm no encontrado: " + id));
    }
}
