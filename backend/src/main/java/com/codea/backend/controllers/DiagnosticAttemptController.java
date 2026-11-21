package com.codea.backend.controllers;

import com.codea.backend.dtos.*;
import com.codea.backend.services.DiagnosticAttemptService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diagnostic-attempts")
@RequiredArgsConstructor
public class DiagnosticAttemptController {

    private final DiagnosticAttemptService service;

    @GetMapping
    public ResponseEntity<List<DiagnosticAttemptDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiagnosticAttemptDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/by-user/{id}")
    public ResponseEntity<List<DiagnosticAttemptDto>> findByUser(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByUser(id));
    }

    @GetMapping("/by-exam/{id}")
    public ResponseEntity<List<DiagnosticAttemptDto>> findByExam(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByExam(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/submit")
    public ResponseEntity<DiagnosticResultDto> submit(@Valid @RequestBody DiagnosticSubmissionDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.submit(request));
    }
}
