package com.codea.backend.controllers;

import com.codea.backend.dtos.*;
import com.codea.backend.services.ActivityAttemptService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity-attempts")
@RequiredArgsConstructor
public class ActivityAttemptController {

    private final ActivityAttemptService service;

    @GetMapping
    public ResponseEntity<List<ActivityAttemptDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityAttemptDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/by-user/{id}")
    public ResponseEntity<List<ActivityAttemptDto>> findByUser(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByUser(id));
    }

    @GetMapping("/by-activity/{id}")
    public ResponseEntity<List<ActivityAttemptDto>> findByActivity(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByActivity(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/submit")
    public ResponseEntity<ActivityResultDto> submit(@Valid @RequestBody ActivitySubmissionDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.submit(request));
    }
}
