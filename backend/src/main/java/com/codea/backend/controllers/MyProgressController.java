package com.codea.backend.controllers;

import com.codea.backend.dtos.ProgressSummaryDto;
import com.codea.backend.services.MyProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/me/progress")
@RequiredArgsConstructor
public class MyProgressController {

    private final MyProgressService service;

    @GetMapping("/summary")
    public ResponseEntity<ProgressSummaryDto> summary(Authentication authentication) {
        return ResponseEntity.ok(service.summary(authentication.getName()));
    }

    @PostMapping("/lessons/{idLesson}/start")
    public ResponseEntity<Void> start(@PathVariable Long idLesson, Authentication authentication) {
        service.startLesson(authentication.getName(), idLesson);
        return ResponseEntity.noContent().build();
    }
}