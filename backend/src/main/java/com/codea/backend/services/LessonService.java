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
public class LessonService {

    private final LessonRepository repository;
    private final LessonMapper mapper;

    @Transactional(readOnly = true)
    public List<LessonDTO> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public LessonDTO findById(Long id) {
        return mapper.toDto(getEntity(id));
    }

    public LessonDTO create(LessonDTO dto) {
        Lesson entity = mapper.toEntity(dto);
        entity.setIdLesson(null);
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    public LessonDTO update(Long id, LessonDTO dto) {
        Lesson entity = getEntity(id);
        mapper.updateEntity(entity, dto);
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Lesson no encontrado: " + id);
        }
        repository.deleteById(id);
        repository.flush();
    }

    @Transactional(readOnly = true)
    public Lesson getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson no encontrado: " + id));
    }
}
