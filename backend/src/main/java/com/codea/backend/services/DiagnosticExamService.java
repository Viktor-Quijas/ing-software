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
public class DiagnosticExamService {

    private final DiagnosticExamRepository repository;
    private final DiagnosticExamMapper mapper;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;

    @Transactional(readOnly = true)
    public List<DiagnosticExamDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public DiagnosticExamDto findById(Long id) {
        return mapper.toDto(getEntity(id));
    }

    public DiagnosticExamDto create(DiagnosticExamDto dto) {
        DiagnosticExam entity = mapper.toEntity(dto);
        entity.setIdExam(null);
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    public DiagnosticExamDto update(Long id, DiagnosticExamDto dto) {
        DiagnosticExam entity = getEntity(id);
        mapper.updateEntity(entity, dto);
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("DiagnosticExam no encontrado: " + id);
        }
        repository.deleteById(id);
        repository.flush();
    }

    @Transactional(readOnly = true)
    public DiagnosticExam getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DiagnosticExam no encontrado: " + id));
    }

    /** Examen completo con preguntas y opciones (sin revelar cuáles son correctas). */
    @Transactional(readOnly = true)
    public ExamDetailDto findDetail(Long id) {
        DiagnosticExam exam = getEntity(id);
        List<QuestionDetailDto> questions = questionRepository.findByExam_IdExam(id).stream()
                .sorted(Comparator.comparing(Question::getIdQuestion))
                .map(q -> QuestionDetailDto.builder()
                        .idQuestion(q.getIdQuestion())
                        .textQuestion(q.getTextQuestion())
                        .topicQuestion(q.getTopicQuestion())
                        .difficultyQuestion(q.getDifficultyQuestion())
                        .options(optionRepository.findByQuestion_IdQuestion(q.getIdQuestion()).stream()
                                .sorted(Comparator.comparing(Option::getIdOption))
                                .map(o -> OptionDetailDto.builder()
                                        .idOption(o.getIdOption())
                                        .textOption(o.getTextOption())
                                        .build())
                                .toList())
                        .build())
                .toList();
        return ExamDetailDto.builder()
                .idExam(exam.getIdExam())
                .nameExam(exam.getNameExam())
                .descriptionExam(exam.getDescriptionExam())
                .statusExam(exam.getStatusExam())
                .questions(questions)
                .build();
    }
}
