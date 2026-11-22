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
public class OptionService {

    private final OptionRepository repository;
    private final OptionMapper mapper;

    @Transactional(readOnly = true)
    public List<OptionDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public OptionDto findById(Long id) {
        return mapper.toDto(getEntity(id));
    }

    @Transactional(readOnly = true)
    public List<OptionDto> findByQuestion(Long id) {
        return repository.findByQuestion_IdQuestion(id).stream().map(mapper::toDto).toList();
    }

    public OptionDto create(OptionDto dto) {
        Option entity = mapper.toEntity(dto);
        entity.setIdOption(null);
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    public OptionDto update(Long id, OptionDto dto) {
        Option entity = getEntity(id);
        mapper.updateEntity(entity, dto);
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Option no encontrado: " + id);
        }
        repository.deleteById(id);
        repository.flush();
    }

    @Transactional(readOnly = true)
    public Option getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Option no encontrado: " + id));
    }
}
