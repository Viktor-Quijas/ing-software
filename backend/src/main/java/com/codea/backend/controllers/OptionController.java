package com.codea.backend.controllers;

import com.codea.backend.dtos.*;
import com.codea.backend.services.OptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/options")
@RequiredArgsConstructor
public class OptionController {

    private final OptionService service;

    @GetMapping
    public ResponseEntity<List<OptionDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OptionDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/by-question/{id}")
    public ResponseEntity<List<OptionDto>> findByQuestion(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByQuestion(id));
    }

    @PostMapping
    public ResponseEntity<OptionDto> create(@Valid @RequestBody OptionDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OptionDto> update(@PathVariable Long id, @Valid @RequestBody OptionDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
