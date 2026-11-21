package com.codea.backend.controllers;

import com.codea.backend.dtos.*;
import com.codea.backend.services.AttemptAnswerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attempt-answers")
@RequiredArgsConstructor
public class AttemptAnswerController {

    private final AttemptAnswerService service;

    @GetMapping
    public ResponseEntity<List<AttemptAnswerDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttemptAnswerDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/by-attempt/{id}")
    public ResponseEntity<List<AttemptAnswerDto>> findByAttempt(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByAttempt(id));
    }

    @GetMapping("/by-question/{id}")
    public ResponseEntity<List<AttemptAnswerDto>> findByQuestion(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByQuestion(id));
    }

    @GetMapping("/by-option/{id}")
    public ResponseEntity<List<AttemptAnswerDto>> findByOption(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByOption(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
