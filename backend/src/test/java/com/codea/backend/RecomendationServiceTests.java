package com.codea.backend;

import com.codea.backend.dtos.RecomendationDTO;
import com.codea.backend.entitys.*;
import com.codea.backend.mappers.RecomendationMapper;
import com.codea.backend.repositorys.*;
import com.codea.backend.services.RecomendationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecomendationServiceTests {

    @Mock RecomendationRepository repository;
    @Mock RecomendationMapper mapper;
    @Mock LessonRepository lessonRepository;
    @Mock ActivityRepository activityRepository;
    @Mock AlgorithmRepository algorithmRepository;
    @Mock ProgressRepository progressRepository;
    @Mock ActivityAttemptRepository activityAttemptRepository;
    @Mock ActivityAttemptAnswerRepository activityAttemptAnswerRepository;
    @InjectMocks RecomendationService service;

    @Test
    void generateFromLatestDiagnosticAttempt_recoversRecommendationForFailedTopic() {
        User user = User.builder().idUser(7L).build();
        Lesson lesson = Lesson.builder()
                .idLesson(12L)
                .titleLesson("Clases y objetos")
                .difficultyLesson("Principiante")
                .build();
        Activity diagnostic = Activity.builder()
                .idActivity(5L)
                .typeActivity("Diagnostico")
                .build();
        ActivityAttempt attempt = ActivityAttempt.builder()
                .idActivityAttempt(20L)
                .user(user)
                .activity(diagnostic)
                .build();
        ActivityQuestion question = ActivityQuestion.builder()
                .topicQuestion("Clases y objetos")
                .difficultyQuestion("Principiante")
                .build();
        ActivityAttemptAnswer failedAnswer = ActivityAttemptAnswer.builder()
                .isCorrect(false)
                .activityQuestion(question)
                .build();
        Recomendation saved = Recomendation.builder().user(user).lesson(lesson).build();
        RecomendationDTO expected = RecomendationDTO.builder().idLesson(12L).build();

        when(repository.findByUser_IdUserOrderByIdRecomendationDesc(7L))
                .thenReturn(List.of(), List.of(saved));
        when(activityAttemptRepository.findByUser_IdUser(7L)).thenReturn(List.of(attempt));
        when(activityAttemptAnswerRepository.findByActivityAttempt_IdActivityAttempt(20L))
                .thenReturn(List.of(failedAnswer));
        when(algorithmRepository.findFirstByOrderByIdAlgorithmAsc()).thenReturn(Optional.empty());
        when(lessonRepository.findAll()).thenReturn(List.of(lesson));
        when(repository.existsByUser_IdUserAndLesson_IdLesson(7L, 12L)).thenReturn(false);
        when(activityRepository.findByLesson_IdLesson(12L)).thenReturn(Optional.empty());
        when(repository.save(any(Recomendation.class))).thenReturn(saved);
        when(mapper.toDto(saved)).thenReturn(expected);

        List<RecomendationDTO> result = service.generateFromLatestDiagnosticAttempt(7L);

        assertEquals(List.of(expected), result);
        verify(repository).save(any(Recomendation.class));
    }
}
