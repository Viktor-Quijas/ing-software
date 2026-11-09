package com.codea.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.codea.backend.dtos.StyleLearningDTO;
import com.codea.backend.services.StyleLearningService;

@RestController
@RequestMapping("/api/styles")
public class StyleLearningController {

    private final StyleLearningService styleService;

    public StyleLearningController(StyleLearningService styleService) {
        this.styleService = styleService;
    }

    @GetMapping
    public List<StyleLearningDTO> findAll() {
        return styleService.findAll();
    }

    @GetMapping("/{id}")
    public StyleLearningDTO findById(@PathVariable Long id) {
        return styleService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StyleLearningDTO create(@RequestBody StyleLearningDTO dto) {
        return styleService.create(dto);
    }

    @PutMapping("/{id}")
    public StyleLearningDTO update(@PathVariable Long id, @RequestBody StyleLearningDTO dto) {
        return styleService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        styleService.delete(id);
    }
}