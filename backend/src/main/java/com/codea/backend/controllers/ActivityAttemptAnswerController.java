package com.codea.backend.controllers;

import com.codea.backend.dtos.*;
import com.codea.backend.services.ActivityAttemptAnswerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity-attempt-answers")
@RequiredArgsConstructor
public class ActivityAttemptAnswerController {

    private final ActivityAttemptAnswerService service;

    @GetMapping
    public ResponseEntity<List<ActivityAttemptAnswerDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityAttemptAnswerDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/by-activity-attempt/{id}")
    public ResponseEntity<List<ActivityAttemptAnswerDto>> findByActivityAttempt(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByActivityAttempt(id));
    }

    @GetMapping("/by-activity-question/{id}")
    public ResponseEntity<List<ActivityAttemptAnswerDto>> findByActivityQuestion(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByActivityQuestion(id));
    }

    @GetMapping("/by-activity-option/{id}")
    public ResponseEntity<List<ActivityAttemptAnswerDto>> findByActivityOption(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByActivityOption(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
