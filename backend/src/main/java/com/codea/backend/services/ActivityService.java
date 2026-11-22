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
public class ActivityService {

    private final ActivityRepository repository;
    private final ActivityMapper mapper;
    private final ActivityQuestionRepository questionRepository;
    private final ActivityOptionRepository optionRepository;

    @Transactional(readOnly = true)
    public List<ActivityDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public ActivityDto findById(Long id) {
        return mapper.toDto(getEntity(id));
    }

    public ActivityDto create(ActivityDto dto) {
        Activity entity = mapper.toEntity(dto);
        entity.setIdActivity(null);
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    public ActivityDto update(Long id, ActivityDto dto) {
        Activity entity = getEntity(id);
        mapper.updateEntity(entity, dto);
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Activity no encontrado: " + id);
        }
        repository.deleteById(id);
        repository.flush();
    }

    @Transactional(readOnly = true)
    public Activity getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activity no encontrado: " + id));
    }

    @Transactional(readOnly = true)
    public ActivityDto findByLesson(Long idLesson) {
        return repository.findByLesson_IdLesson(idLesson)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("La lección " + idLesson + " no tiene actividad"));
    }

    /** Cuestionario completo de la actividad (sin revelar las respuestas correctas). */
    @Transactional(readOnly = true)
    public ActivityDetailDto findDetail(Long id) {
        Activity activity = getEntity(id);
        List<ActivityQuestionDetailDto> questions = questionRepository.findByActivity_IdActivity(id).stream()
                .sorted(Comparator.comparing(ActivityQuestion::getOrderQuestion,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .map(q -> ActivityQuestionDetailDto.builder()
                        .idActivityQuestion(q.getIdActivityQuestion())
                        .textQuestion(q.getTextQuestion())
                        .topicQuestion(q.getTopicQuestion())
                        .difficultyQuestion(q.getDifficultyQuestion())
                        .orderQuestion(q.getOrderQuestion())
                        .typeQuestion(q.getTypeQuestion())
                        .options(optionRepository.findByActivityQuestion_IdActivityQuestion(q.getIdActivityQuestion()).stream()
                                .sorted(Comparator.comparing(ActivityOption::getOrderOption,
                                        Comparator.nullsLast(Comparator.naturalOrder())))
                                .map(o -> ActivityOptionDetailDto.builder()
                                        .idActivityOption(o.getIdActivityOption())
                                        .textOption(o.getTextOption())
                                        .orderOption(o.getOrderOption())
                                        .build())
                                .toList())
                        .build())
                .toList();
        return ActivityDetailDto.builder()
                .idActivity(activity.getIdActivity())
                .idLesson(activity.getLesson().getIdLesson())
                .titleActivity(activity.getTitleActivity())
                .descriptionActivity(activity.getDescriptionActivity())
                .typeActivity(activity.getTypeActivity())
                .instructionActivity(activity.getInstructionActivity())
                .lengthActivity(activity.getLengthActivity())
                .passingScore(activity.getPassingScore())
                .questions(questions)
                .build();
    }
}
