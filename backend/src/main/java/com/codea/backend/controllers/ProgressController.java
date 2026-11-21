package com.codea.backend.controllers;

import com.codea.backend.dtos.*;
import com.codea.backend.services.ProgressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService service;

    @GetMapping
    public ResponseEntity<List<ProgressDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgressDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/by-user/{id}")
    public ResponseEntity<List<ProgressDto>> findByUser(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByUser(id));
    }

    @GetMapping("/by-activity/{id}")
    public ResponseEntity<List<ProgressDto>> findByActivity(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByActivity(id));
    }

    @PostMapping
    public ResponseEntity<ProgressDto> create(@Valid @RequestBody ProgressDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgressDto> update(@PathVariable Long id, @Valid @RequestBody ProgressDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
