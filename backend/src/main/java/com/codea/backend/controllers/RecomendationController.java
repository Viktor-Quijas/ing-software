package com.codea.backend.controllers;

import com.codea.backend.dtos.*;
import com.codea.backend.services.RecomendationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recomendations")
@RequiredArgsConstructor
public class RecomendationController {

    private final RecomendationService service;

    @GetMapping
    public ResponseEntity<List<RecomendationDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecomendationDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/by-user/{id}")
    public ResponseEntity<List<RecomendationDto>> findByUser(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByUser(id));
    }

    @GetMapping("/by-algorithm/{id}")
    public ResponseEntity<List<RecomendationDto>> findByAlgorithm(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByAlgorithm(id));
    }

    @GetMapping("/by-lesson/{id}")
    public ResponseEntity<List<RecomendationDto>> findByLesson(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByLesson(id));
    }

    @GetMapping("/by-activity/{id}")
    public ResponseEntity<List<RecomendationDto>> findByActivity(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByActivity(id));
    }

    @PostMapping
    public ResponseEntity<RecomendationDto> create(@Valid @RequestBody RecomendationDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecomendationDto> update(@PathVariable Long id, @Valid @RequestBody RecomendationDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
