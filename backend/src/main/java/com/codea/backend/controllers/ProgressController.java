package com.codea.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.codea.backend.dtos.ProgressDTO;
import com.codea.backend.services.ProgressService;

@RestController
@RequestMapping("/api")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @GetMapping("/users/{idUser}/progress")
    public List<ProgressDTO> findByUser(@PathVariable Long idUser) {
        return progressService.findByUser(idUser);
    }

    @PostMapping("/users/{idUser}/activities/{idActivity}/progress")
    @ResponseStatus(HttpStatus.CREATED)
    public ProgressDTO create(@PathVariable Long idUser, @PathVariable Long idActivity, @RequestBody ProgressDTO dto) {
        return progressService.create(idUser, idActivity, dto);
    }

    @GetMapping("/progress/{id}")
    public ProgressDTO findById(@PathVariable Long id) {
        return progressService.findById(id);
    }

    @PutMapping("/progress/{id}")
    public ProgressDTO update(@PathVariable Long id, @RequestBody ProgressDTO dto) {
        return progressService.update(id, dto);
    }

    @DeleteMapping("/progress/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        progressService.delete(id);
    }
}