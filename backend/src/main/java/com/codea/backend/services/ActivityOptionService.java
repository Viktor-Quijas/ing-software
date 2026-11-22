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
public class ActivityOptionService {

    private final ActivityOptionRepository repository;
    private final ActivityOptionMapper mapper;

    @Transactional(readOnly = true)
    public List<ActivityOptionDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public ActivityOptionDto findById(Long id) {
        return mapper.toDto(getEntity(id));
    }

    @Transactional(readOnly = true)
    public List<ActivityOptionDto> findByActivityQuestion(Long id) {
        return repository.findByActivityQuestion_IdActivityQuestion(id).stream().map(mapper::toDto).toList();
    }

    public ActivityOptionDto create(ActivityOptionDto dto) {
        ActivityOption entity = mapper.toEntity(dto);
        entity.setIdActivityOption(null);
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    public ActivityOptionDto update(Long id, ActivityOptionDto dto) {
        ActivityOption entity = getEntity(id);
        mapper.updateEntity(entity, dto);
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("ActivityOption no encontrado: " + id);
        }
        repository.deleteById(id);
        repository.flush();
    }

    @Transactional(readOnly = true)
    public ActivityOption getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ActivityOption no encontrado: " + id));
    }
}
