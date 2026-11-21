package com.codea.backend.controllers;

import com.codea.backend.dtos.*;
import com.codea.backend.services.ActivityOptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity-options")
@RequiredArgsConstructor
public class ActivityOptionController {

    private final ActivityOptionService service;

    @GetMapping
    public ResponseEntity<List<ActivityOptionDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityOptionDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/by-activity-question/{id}")
    public ResponseEntity<List<ActivityOptionDto>> findByActivityQuestion(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByActivityQuestion(id));
    }

    @PostMapping
    public ResponseEntity<ActivityOptionDto> create(@Valid @RequestBody ActivityOptionDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityOptionDto> update(@PathVariable Long id, @Valid @RequestBody ActivityOptionDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
