package com.codea.backend.controllers;

import com.codea.backend.dtos.*;
import com.codea.backend.services.DiagnosticExamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diagnostic-exams")
@RequiredArgsConstructor
public class DiagnosticExamController {

    private final DiagnosticExamService service;

    @GetMapping
    public ResponseEntity<List<DiagnosticExamDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiagnosticExamDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<DiagnosticExamDto> create(@Valid @RequestBody DiagnosticExamDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiagnosticExamDto> update(@PathVariable Long id, @Valid @RequestBody DiagnosticExamDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/detail")
    public ResponseEntity<ExamDetailDto> findDetail(@PathVariable Long id) {
        return ResponseEntity.ok(service.findDetail(id));
    }
}
