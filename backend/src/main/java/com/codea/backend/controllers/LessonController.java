package com.codea.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.codea.backend.dtos.LessonDTO;
import com.codea.backend.services.LessonService;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public List<LessonDTO> findAll() {
        return lessonService.findAll();
    }

    @GetMapping("/{id}")
    public LessonDTO findById(@PathVariable Long id) {
        return lessonService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LessonDTO create(@RequestBody LessonDTO dto) {
        return lessonService.create(dto);
    }

    @PutMapping("/{id}")
    public LessonDTO update(@PathVariable Long id, @RequestBody LessonDTO dto) {
        return lessonService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        lessonService.delete(id);
    }
}