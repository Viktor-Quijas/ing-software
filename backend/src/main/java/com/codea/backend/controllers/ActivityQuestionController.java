package com.codea.backend.controllers;

import com.codea.backend.dtos.*;
import com.codea.backend.services.ActivityQuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity-questions")
@RequiredArgsConstructor
public class ActivityQuestionController {

    private final ActivityQuestionService service;

    @GetMapping
    public ResponseEntity<List<ActivityQuestionDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityQuestionDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/by-activity/{id}")
    public ResponseEntity<List<ActivityQuestionDto>> findByActivity(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByActivity(id));
    }

    @PostMapping
    public ResponseEntity<ActivityQuestionDto> create(@Valid @RequestBody ActivityQuestionDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityQuestionDto> update(@PathVariable Long id, @Valid @RequestBody ActivityQuestionDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
