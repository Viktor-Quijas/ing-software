package com.codea.backend.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.codea.backend.dtos.StyleLearningDTO;
import com.codea.backend.entitys.StyleLearning;
import com.codea.backend.mappers.StyleLearningMapper;
import com.codea.backend.repositorys.StyleLearningRepository;

@Service
public class StyleLearningService {

    private final StyleLearningRepository styleRepository;
    private final StyleLearningMapper styleMapper;

    public StyleLearningService(StyleLearningRepository styleRepository, StyleLearningMapper styleMapper) {
        this.styleRepository = styleRepository;
        this.styleMapper = styleMapper;
    }

    public List<StyleLearningDTO> findAll() {
        return styleRepository.findAll().stream().map(styleMapper::toDTO).toList();
    }

    public StyleLearningDTO findById(Long id) {
        return styleMapper.toDTO(getStyle(id));
    }

    public StyleLearningDTO create(StyleLearningDTO dto) {
        return styleMapper.toDTO(styleRepository.save(styleMapper.toEntity(dto)));
    }

    public StyleLearningDTO update(Long id, StyleLearningDTO dto) {
        StyleLearning style = getStyle(id);
        styleMapper.updateEntity(dto, style);
        return styleMapper.toDTO(styleRepository.save(style));
    }

    public void delete(Long id) {
        getStyle(id);
        styleRepository.deleteById(id);
    }

    private StyleLearning getStyle(Long id) {
        return styleRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Estilo no encontrado con id " + id));
    }
}