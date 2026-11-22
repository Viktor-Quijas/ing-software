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
public class AttemptAnswerService {

    private final AttemptAnswerRepository repository;
    private final AttemptAnswerMapper mapper;

    @Transactional(readOnly = true)
    public List<AttemptAnswerDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public AttemptAnswerDto findById(Long id) {
        return mapper.toDto(getEntity(id));
    }

    @Transactional(readOnly = true)
    public List<AttemptAnswerDto> findByAttempt(Long id) {
        return repository.findByAttempt_IdAttempt(id).stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<AttemptAnswerDto> findByQuestion(Long id) {
        return repository.findByQuestion_IdQuestion(id).stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<AttemptAnswerDto> findByOption(Long id) {
        return repository.findByOption_IdOption(id).stream().map(mapper::toDto).toList();
    }

    public AttemptAnswerDto create(AttemptAnswerDto dto) {
        AttemptAnswer entity = mapper.toEntity(dto);
        entity.setIdAnswer(null);
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    public AttemptAnswerDto update(Long id, AttemptAnswerDto dto) {
        AttemptAnswer entity = getEntity(id);
        mapper.updateEntity(entity, dto);
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("AttemptAnswer no encontrado: " + id);
        }
        repository.deleteById(id);
        repository.flush();
    }

    @Transactional(readOnly = true)
    public AttemptAnswer getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AttemptAnswer no encontrado: " + id));
    }
}
