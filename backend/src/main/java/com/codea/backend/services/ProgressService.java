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
public class ProgressService {

    private final ProgressRepository repository;
    private final ProgressMapper mapper;

    @Transactional(readOnly = true)
    public List<ProgressDTO> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public ProgressDTO findById(Long id) {
        return mapper.toDto(getEntity(id));
    }

    @Transactional(readOnly = true)
    public List<ProgressDTO> findByUser(Long id) {
        return repository.findByUser_IdUser(id).stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<ProgressDTO> findByActivity(Long id) {
        return repository.findByActivity_IdActivity(id).stream().map(mapper::toDto).toList();
    }

    public ProgressDTO create(ProgressDTO dto) {
        Progress entity = mapper.toEntity(dto);
        entity.setIdProgress(null);
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    public ProgressDTO update(Long id, ProgressDTO dto) {
        Progress entity = getEntity(id);
        mapper.updateEntity(entity, dto);
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Progress no encontrado: " + id);
        }
        repository.deleteById(id);
        repository.flush();
    }

    @Transactional(readOnly = true)
    public Progress getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Progress no encontrado: " + id));
    }
}
