package com.codea.backend.services;

import com.codea.backend.dtos.*;
import com.codea.backend.entitys.*;
import com.codea.backend.exceptions.*;
import com.codea.backend.mappers.*;
import com.codea.backend.repositorys.*;
import com.codea.backend.utils.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ActivityAttemptService {

    private final ActivityAttemptRepository repository;
    private final ActivityAttemptMapper mapper;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final ActivityQuestionRepository questionRepository;
    private final ActivityOptionRepository optionRepository;
    private final ActivityAttemptAnswerRepository answerRepository;
    private final ProgressRepository progressRepository;
    private final RecomendationRepository recomendationRepository;

    @Transactional(readOnly = true)
    public List<ActivityAttemptDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public ActivityAttemptDto findById(Long id) {
        return mapper.toDto(getEntity(id));
    }

    @Transactional(readOnly = true)
    public List<ActivityAttemptDto> findByUser(Long id) {
        return repository.findByUser_IdUser(id).stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<ActivityAttemptDto> findByActivity(Long id) {
        return repository.findByActivity_IdActivity(id).stream().map(mapper::toDto).toList();
    }

    public ActivityAttemptDto create(ActivityAttemptDto dto) {
        ActivityAttempt entity = mapper.toEntity(dto);
        entity.setIdActivityAttempt(null);
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    public ActivityAttemptDto update(Long id, ActivityAttemptDto dto) {
        ActivityAttempt entity = getEntity(id);
        mapper.updateEntity(entity, dto);
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("ActivityAttempt no encontrado: " + id);
        }
        repository.deleteById(id);
        repository.flush();
    }

    @Transactional(readOnly = true)
    public ActivityAttempt getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ActivityAttempt no encontrado: " + id));
    }

    public ActivityResultDto submit(ActivitySubmissionDto request) {
        User user = userRepository.findById(request.getIdUser())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + request.getIdUser()));
        Activity activity = activityRepository.findById(request.getIdActivity())
                .orElseThrow(() -> new ResourceNotFoundException("Actividad no encontrada: " + request.getIdActivity()));

        Map<Long, ActivityQuestion> questions = questionRepository.findByActivity_IdActivity(activity.getIdActivity()).stream()
                .collect(Collectors.toMap(ActivityQuestion::getIdActivityQuestion, q -> q));

        Set<Long> answered = new HashSet<>();
        List<ActivityAttemptAnswer> answers = new ArrayList<>();
        double score = 0;

        for (AnswerSubmissionDto a : request.getAnswers()) {
            ActivityQuestion question = questions.get(a.getIdQuestion());
            if (question == null) {
                throw new BadRequestException("La pregunta " + a.getIdQuestion() + " no pertenece a la actividad");
            }
            if (!answered.add(question.getIdActivityQuestion())) {
                throw new BadRequestException("La pregunta " + a.getIdQuestion() + " está repetida");
            }
            ActivityOption option = optionRepository.findById(a.getIdOption())
                    .orElseThrow(() -> new ResourceNotFoundException("Opción no encontrada: " + a.getIdOption()));
            if (!option.getActivityQuestion().getIdActivityQuestion().equals(question.getIdActivityQuestion())) {
                throw new BadRequestException("La opción " + a.getIdOption() + " no pertenece a la pregunta " + a.getIdQuestion());
            }
            boolean correct = Boolean.TRUE.equals(option.getIsCorrect());
            if (correct) score += 1;
            answers.add(ActivityAttemptAnswer.builder()
                    .activityQuestion(question).activityOption(option).isCorrect(correct).build());
        }
        if (answered.size() != questions.size()) {
            throw new BadRequestException("Debes responder todas las preguntas de la actividad");
        }

        double passingScore = activity.getPassingScore() == null ? 0.0 : activity.getPassingScore();
        boolean passed = score >= passingScore;

        ActivityAttempt attempt = repository.save(ActivityAttempt.builder()
                .user(user).activity(activity)
                .scoreAttempt(score).dateAttempt(LocalDate.now())
                .statusAttempt(passed ? "APROBADO" : "REPROBADO")
                .build());
        answers.forEach(a -> a.setActivityAttempt(attempt));
        answerRepository.saveAll(answers);

        // PROGRESS (un registro por usuario + actividad)
        Progress progress = progressRepository
                .findByUser_IdUserAndActivity_IdActivity(user.getIdUser(), activity.getIdActivity())
                .orElseGet(() -> Progress.builder().user(user).activity(activity).triesProgress(0).build());
        progress.setTriesProgress((progress.getTriesProgress() == null ? 0 : progress.getTriesProgress()) + 1);
        progress.setGradeProgress(Math.max(progress.getGradeProgress() == null ? 0.0 : progress.getGradeProgress(), score));
        progress.setDateProgress(LocalDate.now());
        progress.setLastAccessProgress(LocalDateTime.now());
        if (passed) {
            progress.setStatusProgress("COMPLETADO");
        } else if (!"COMPLETADO".equalsIgnoreCase(progress.getStatusProgress())) {
            progress.setStatusProgress("EN_PROGRESO");
        }
        progressRepository.save(progress);

        // Al terminar la actividad se termina la lección y se borra de recomendaciones
        if (passed) {
            recomendationRepository.deleteByUser_IdUserAndLesson_IdLesson(
                    user.getIdUser(), activity.getLesson().getIdLesson());
        }

        return ActivityResultDto.builder()
                .idActivityAttempt(attempt.getIdActivityAttempt())
                .scoreAttempt(score)
                .totalQuestions(questions.size())
                .passingScore(passingScore)
                .passed(passed)
                .statusAttempt(attempt.getStatusAttempt())
                .build();
    }
}
