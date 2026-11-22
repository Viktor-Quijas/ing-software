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
public class StyleLearningService {

    private final StyleLearningRepository repository;
    private final StyleLearningMapper mapper;

    @Transactional(readOnly = true)
    public List<StyleLearningDTO> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public StyleLearningDTO findById(Long id) {
        return mapper.toDto(getEntity(id));
    }

    public StyleLearningDTO create(StyleLearningDTO dto) {
        StyleLearning entity = mapper.toEntity(dto);
        entity.setIdStyle(null);
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    public StyleLearningDTO update(Long id, StyleLearningDTO dto) {
        StyleLearning entity = getEntity(id);
        mapper.updateEntity(entity, dto);
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("StyleLearning no encontrado: " + id);
        }
        repository.deleteById(id);
        repository.flush();
    }

    @Transactional(readOnly = true)
    public StyleLearning getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("StyleLearning no encontrado: " + id));
    }
}
