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
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RecomendationService {

    private final RecomendationRepository repository;
    private final RecomendationMapper mapper;
    private final LessonRepository lessonRepository;
    private final ActivityRepository activityRepository;
    private final AlgorithmRepository algorithmRepository;
    private final ProgressRepository progressRepository;

    @Transactional(readOnly = true)
    public List<RecomendationDTO> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public RecomendationDTO findById(Long id) {
        return mapper.toDto(getEntity(id));
    }

    @Transactional(readOnly = true)
    public List<RecomendationDTO> findByUser(Long id) {
        return repository.findByUser_IdUser(id).stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<RecomendationDTO> findByAlgorithm(Long id) {
        return repository.findByAlgorithm_IdAlgorithm(id).stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<RecomendationDTO> findByLesson(Long id) {
        return repository.findByLesson_IdLesson(id).stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<RecomendationDTO> findByActivity(Long id) {
        return repository.findByActivity_IdActivity(id).stream().map(mapper::toDto).toList();
    }

    public RecomendationDTO create(RecomendationDTO dto) {
        Recomendation entity = mapper.toEntity(dto);
        entity.setIdRecomendation(null);
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    public RecomendationDTO update(Long id, RecomendationDTO dto) {
        Recomendation entity = getEntity(id);
        mapper.updateEntity(entity, dto);
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recomendation no encontrado: " + id);
        }
        repository.deleteById(id);
        repository.flush();
    }

    @Transactional(readOnly = true)
    public Recomendation getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recomendation no encontrado: " + id));
    }

    /**
     * Genera recomendaciones a partir de las preguntas falladas del examen diagnóstico.
     * Se recomienda una lección solo si el tópico de la pregunta es igual al título de la lección
     * y la dificultad es la misma (sin distinguir mayúsculas ni espacios).
     */
    public List<RecomendationDTO> generateFromFailedQuestions(User user, List<Question> failedQuestions) {
        Algorithm algorithm = algorithmRepository.findFirstByOrderByIdAlgorithmAsc().orElse(null);
        List<Lesson> lessons = lessonRepository.findAll();
        Set<Long> seenLessons = new HashSet<>();
        List<Recomendation> created = new ArrayList<>();

        for (Question question : failedQuestions) {
            String topic = TextUtils.normalize(question.getTopicQuestion());
            String difficulty = TextUtils.normalize(question.getDifficultyQuestion());
            if (topic.isEmpty()) continue;

            for (Lesson lesson : lessons) {
                if (!topic.equals(TextUtils.normalize(lesson.getTitleLesson()))
                        || !difficulty.equals(TextUtils.normalize(lesson.getDifficultyLesson()))) {
                    continue;
                }
                if (!seenLessons.add(lesson.getIdLesson())) continue;
                if (repository.existsByUser_IdUserAndLesson_IdLesson(user.getIdUser(), lesson.getIdLesson())) continue;

                Activity activity = activityRepository.findByLesson_IdLesson(lesson.getIdLesson()).orElse(null);
                if (activity != null && isCompleted(user, activity)) continue;

                created.add(repository.save(Recomendation.builder()
                        .user(user)
                        .algorithm(algorithm)
                        .lesson(lesson)
                        .activity(activity)
                        .purposeRecomendation("REFUERZO")
                        .descriptionRecomendation("Refuerza el tema \"" + lesson.getTitleLesson()
                                + "\" (dificultad " + lesson.getDifficultyLesson() + ")")
                        .dateRecomendation(LocalDate.now())
                        .statusRecomendation("PENDIENTE")
                        .build()));
            }
        }
        return created.stream().map(mapper::toDto).toList();
    }

    private boolean isCompleted(User user, Activity activity) {
        return progressRepository.findByUser_IdUserAndActivity_IdActivity(user.getIdUser(), activity.getIdActivity())
                .map(p -> "COMPLETADO".equalsIgnoreCase(p.getStatusProgress()))
                .orElse(false);
    }
}
