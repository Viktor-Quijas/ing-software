package com.codea.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.codea.backend.dtos.ActivityDTO;
import com.codea.backend.services.ActivityService;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping
    public List<ActivityDTO> findAll(@RequestParam(required = false) Long idLesson) {
        return idLesson == null ? activityService.findAll() : activityService.findByLesson(idLesson);
    }

    @GetMapping("/{id}")
    public ActivityDTO findById(@PathVariable Long id) {
        return activityService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ActivityDTO create(@RequestBody ActivityDTO dto) {
        return activityService.create(dto);
    }

    @PutMapping("/{id}")
    public ActivityDTO update(@PathVariable Long id, @RequestBody ActivityDTO dto) {
        return activityService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        activityService.delete(id);
    }
}