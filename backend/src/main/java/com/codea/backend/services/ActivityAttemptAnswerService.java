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
public class ActivityAttemptAnswerService {

    private final ActivityAttemptAnswerRepository repository;
    private final ActivityAttemptAnswerMapper mapper;

    @Transactional(readOnly = true)
    public List<ActivityAttemptAnswerDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public ActivityAttemptAnswerDto findById(Long id) {
        return mapper.toDto(getEntity(id));
    }

    @Transactional(readOnly = true)
    public List<ActivityAttemptAnswerDto> findByActivityAttempt(Long id) {
        return repository.findByActivityAttempt_IdActivityAttempt(id).stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<ActivityAttemptAnswerDto> findByActivityQuestion(Long id) {
        return repository.findByActivityQuestion_IdActivityQuestion(id).stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<ActivityAttemptAnswerDto> findByActivityOption(Long id) {
        return repository.findByActivityOption_IdActivityOption(id).stream().map(mapper::toDto).toList();
    }

    public ActivityAttemptAnswerDto create(ActivityAttemptAnswerDto dto) {
        ActivityAttemptAnswer entity = mapper.toEntity(dto);
        entity.setIdAnswer(null);
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    public ActivityAttemptAnswerDto update(Long id, ActivityAttemptAnswerDto dto) {
        ActivityAttemptAnswer entity = getEntity(id);
        mapper.updateEntity(entity, dto);
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("ActivityAttemptAnswer no encontrado: " + id);
        }
        repository.deleteById(id);
        repository.flush();
    }

    @Transactional(readOnly = true)
    public ActivityAttemptAnswer getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ActivityAttemptAnswer no encontrado: " + id));
    }
}
