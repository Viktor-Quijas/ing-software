package com.codea.backend.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.codea.backend.dtos.LessonDTO;
import com.codea.backend.entitys.Lesson;
import com.codea.backend.mappers.LessonMapper;
import com.codea.backend.repositorys.LessonRepository;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    public LessonService(LessonRepository lessonRepository, LessonMapper lessonMapper) {
        this.lessonRepository = lessonRepository;
        this.lessonMapper = lessonMapper;
    }

    public List<LessonDTO> findAll() {
        return lessonRepository.findAll().stream()
                .map(lessonMapper::toDTO)
                .toList();
    }

    public LessonDTO findById(Long id) {
        return lessonMapper.toDTO(getLesson(id));
    }

    public LessonDTO create(LessonDTO dto) {
        Lesson lesson = lessonMapper.toEntity(dto);
        return lessonMapper.toDTO(lessonRepository.save(lesson));
    }

    public LessonDTO update(Long id, LessonDTO dto) {
        Lesson lesson = getLesson(id);
        lessonMapper.updateEntity(dto, lesson);
        return lessonMapper.toDTO(lessonRepository.save(lesson));
    }

    public void delete(Long id) {
        getLesson(id); // lanza 404 si no existe
        lessonRepository.deleteById(id);
    }

    private Lesson getLesson(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Lección no encontrada con id " + id));
    }
}