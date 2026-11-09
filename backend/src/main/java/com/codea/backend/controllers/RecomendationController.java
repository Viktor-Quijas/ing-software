package com.codea.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.codea.backend.dtos.RecomendationDTO;
import com.codea.backend.services.RecomendationService;

@RestController
@RequestMapping("/api")
public class RecomendationController {

    private final RecomendationService recomendationService;

    public RecomendationController(RecomendationService recomendationService) {
        this.recomendationService = recomendationService;
    }

    @GetMapping("/users/{idUser}/recomendations")
    public List<RecomendationDTO> findByUser(@PathVariable Long idUser) {
        return recomendationService.findByUser(idUser);
    }

    @PostMapping("/recomendations")
    @ResponseStatus(HttpStatus.CREATED)
    public RecomendationDTO create(@RequestBody RecomendationDTO dto, @RequestParam(required = false) Long idAlgorithm, @RequestParam(required = false) Long idLesson, @RequestParam(required = false) Long idActivity) {
        return recomendationService.create(dto, idAlgorithm, idLesson, idActivity);
    }

    @GetMapping("/recomendations/{id}")
    public RecomendationDTO findById(@PathVariable Long id) {
        return recomendationService.findById(id);
    }

    @PutMapping("/recomendations/{id}")
    public RecomendationDTO update(@PathVariable Long id, @RequestBody RecomendationDTO dto) {
        return recomendationService.update(id, dto);
    }

    @DeleteMapping("/recomendations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        recomendationService.delete(id);
    }
}