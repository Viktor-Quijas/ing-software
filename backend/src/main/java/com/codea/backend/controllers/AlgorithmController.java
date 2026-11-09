package com.codea.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.codea.backend.dtos.AlgorithmDTO;
import com.codea.backend.services.AlgorithmService;

@RestController
@RequestMapping("/api/algorithms")
public class AlgorithmController {

    private final AlgorithmService algorithmService;

    public AlgorithmController(AlgorithmService algorithmService) {
        this.algorithmService = algorithmService;
    }

    @GetMapping
    public List<AlgorithmDTO> findAll() {
        return algorithmService.findAll();
    }

    @GetMapping("/{id}")
    public AlgorithmDTO findById(@PathVariable Long id) {
        return algorithmService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlgorithmDTO create(@RequestBody AlgorithmDTO dto) {
        return algorithmService.create(dto);
    }

    @PutMapping("/{id}")
    public AlgorithmDTO update(@PathVariable Long id, @RequestBody AlgorithmDTO dto) {
        return algorithmService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        algorithmService.delete(id);
    }
}