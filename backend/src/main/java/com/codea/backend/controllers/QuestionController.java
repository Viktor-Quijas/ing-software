package com.codea.backend.controllers;

import com.codea.backend.dtos.*;
import com.codea.backend.services.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService service;

    @GetMapping
    public ResponseEntity<List<QuestionDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/by-exam/{id}")
    public ResponseEntity<List<QuestionDto>> findByExam(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByExam(id));
    }

    @PostMapping
    public ResponseEntity<QuestionDto> create(@Valid @RequestBody QuestionDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionDto> update(@PathVariable Long id, @Valid @RequestBody QuestionDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
