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
    public ResponseEntity<List<RecomendationDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecomendationDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/by-user/{id}")
    public ResponseEntity<List<RecomendationDTO>> findByUser(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByUser(id));
    }

    @PostMapping("/by-user/{id}/generate-from-diagnostic")
    public ResponseEntity<List<RecomendationDTO>> generateFromLatestDiagnosticAttempt(
            @PathVariable Long id) {
        return ResponseEntity.ok(service.generateFromLatestDiagnosticAttempt(id));
    }

    @GetMapping("/by-algorithm/{id}")
    public ResponseEntity<List<RecomendationDTO>> findByAlgorithm(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByAlgorithm(id));
    }

    @GetMapping("/by-lesson/{id}")
    public ResponseEntity<List<RecomendationDTO>> findByLesson(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByLesson(id));
    }

    @GetMapping("/by-activity/{id}")
    public ResponseEntity<List<RecomendationDTO>> findByActivity(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByActivity(id));
    }

    @PostMapping
    public ResponseEntity<RecomendationDTO> create(@Valid @RequestBody RecomendationDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecomendationDTO> update(@PathVariable Long id, @Valid @RequestBody RecomendationDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
