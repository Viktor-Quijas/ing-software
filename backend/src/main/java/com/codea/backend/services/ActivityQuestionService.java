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
public class ActivityQuestionService {

    private final ActivityQuestionRepository repository;
    private final ActivityQuestionMapper mapper;

    @Transactional(readOnly = true)
    public List<ActivityQuestionDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public ActivityQuestionDto findById(Long id) {
        return mapper.toDto(getEntity(id));
    }

    @Transactional(readOnly = true)
    public List<ActivityQuestionDto> findByActivity(Long id) {
        return repository.findByActivity_IdActivity(id).stream().map(mapper::toDto).toList();
    }

    public ActivityQuestionDto create(ActivityQuestionDto dto) {
        ActivityQuestion entity = mapper.toEntity(dto);
        entity.setIdActivityQuestion(null);
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    public ActivityQuestionDto update(Long id, ActivityQuestionDto dto) {
        ActivityQuestion entity = getEntity(id);
        mapper.updateEntity(entity, dto);
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("ActivityQuestion no encontrado: " + id);
        }
        repository.deleteById(id);
        repository.flush();
    }

    @Transactional(readOnly = true)
    public ActivityQuestion getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ActivityQuestion no encontrado: " + id));
    }
}
