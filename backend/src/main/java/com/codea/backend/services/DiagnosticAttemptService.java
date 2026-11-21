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
public class DiagnosticAttemptService {

    private final DiagnosticAttemptRepository repository;
    private final DiagnosticAttemptMapper mapper;
    private final UserRepository userRepository;
    private final DiagnosticExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final AttemptAnswerRepository attemptAnswerRepository;
    private final StyleLearningRepository styleRepository;
    private final RecomendationService recomendationService;

    @Transactional(readOnly = true)
    public List<DiagnosticAttemptDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public DiagnosticAttemptDto findById(Long id) {
        return mapper.toDto(getEntity(id));
    }

    @Transactional(readOnly = true)
    public List<DiagnosticAttemptDto> findByUser(Long id) {
        return repository.findByUser_IdUser(id).stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<DiagnosticAttemptDto> findByExam(Long id) {
        return repository.findByExam_IdExam(id).stream().map(mapper::toDto).toList();
    }

    public DiagnosticAttemptDto create(DiagnosticAttemptDto dto) {
        DiagnosticAttempt entity = mapper.toEntity(dto);
        entity.setIdAttempt(null);
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    public DiagnosticAttemptDto update(Long id, DiagnosticAttemptDto dto) {
        DiagnosticAttempt entity = getEntity(id);
        mapper.updateEntity(entity, dto);
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("DiagnosticAttempt no encontrado: " + id);
        }
        repository.deleteById(id);
        repository.flush();
    }

    @Transactional(readOnly = true)
    public DiagnosticAttempt getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DiagnosticAttempt no encontrado: " + id));
    }

    /**
     * Califica el examen diagnóstico:
     *  - Las preguntas con tópico VISUAL / AUDITIVO / KINESTESICO (respuesta "sí"/"no") definen el estilo
     *    de aprendizaje del usuario y NO cuentan para el score.
     *  - El score es +1 por cada pregunta correcta.
     *  - Por cada respuesta incorrecta se generan recomendaciones (ver RecomendationService).
     */
    public DiagnosticResultDto submit(DiagnosticSubmissionDto request) {
        User user = userRepository.findById(request.getIdUser())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + request.getIdUser()));
        DiagnosticExam exam = examRepository.findById(request.getIdExam())
                .orElseThrow(() -> new ResourceNotFoundException("Examen no encontrado: " + request.getIdExam()));

        Map<Long, Question> questions = questionRepository.findByExam_IdExam(exam.getIdExam()).stream()
                .collect(Collectors.toMap(Question::getIdQuestion, q -> q));

        Set<Long> answered = new HashSet<>();
        Set<String> styles = new LinkedHashSet<>();
        List<Question> failed = new ArrayList<>();
        List<AttemptAnswer> answers = new ArrayList<>();
        double score = 0;
        int scoredQuestions = 0;

        for (AnswerSubmissionDto a : request.getAnswers()) {
            Question question = questions.get(a.getIdQuestion());
            if (question == null) {
                throw new BadRequestException("La pregunta " + a.getIdQuestion() + " no pertenece al examen");
            }
            if (!answered.add(question.getIdQuestion())) {
                throw new BadRequestException("La pregunta " + a.getIdQuestion() + " está repetida");
            }
            Option option = optionRepository.findById(a.getIdOption())
                    .orElseThrow(() -> new ResourceNotFoundException("Opción no encontrada: " + a.getIdOption()));
            if (!option.getQuestion().getIdQuestion().equals(question.getIdQuestion())) {
                throw new BadRequestException("La opción " + a.getIdOption() + " no pertenece a la pregunta " + a.getIdQuestion());
            }

            String styleOfQuestion = StyleCatalog.styleOfTopic(question.getTopicQuestion());
            Boolean correct = null; // las preguntas de estilo no son "correctas" ni "incorrectas"
            if (styleOfQuestion != null) {
                if (StyleCatalog.isYes(option.getTextOption())) {
                    styles.add(styleOfQuestion);
                }
            } else {
                scoredQuestions++;
                correct = Boolean.TRUE.equals(option.getIsCorrect());
                if (correct) {
                    score += 1;
                } else {
                    failed.add(question);
                }
            }
            answers.add(AttemptAnswer.builder().question(question).option(option).isCorrect(correct).build());
        }
        if (answered.size() != questions.size()) {
            throw new BadRequestException("Debes responder todas las preguntas del examen");
        }

        // Estilo de aprendizaje: si no se puede determinar (ninguno o los tres en "sí") se deja el que ya tenía
        String styleName = StyleCatalog.combinationName(styles);
        if (styleName != null) {
            StyleLearning style = styleRepository.findByNameStyleIgnoreCase(styleName)
                    .orElseThrow(() -> new ResourceNotFoundException("No existe el estilo de aprendizaje: " + styleName));
            user.setStyleLearning(style);
            userRepository.save(user);
        }

        DiagnosticAttempt attempt = repository.save(DiagnosticAttempt.builder()
                .user(user).exam(exam).scoreAttempt(score).dateAttempt(LocalDate.now()).build());
        answers.forEach(a -> a.setAttempt(attempt));
        attemptAnswerRepository.saveAll(answers);

        List<RecomendationDto> recomendations = recomendationService.generateFromFailedQuestions(user, failed);

        StyleLearning current = user.getStyleLearning();
        return DiagnosticResultDto.builder()
                .idAttempt(attempt.getIdAttempt())
                .scoreAttempt(score)
                .totalQuestions(scoredQuestions)
                .idStyle(current == null ? null : current.getIdStyle())
                .styleName(current == null ? null : current.getNameStyle())
                .recomendations(recomendations)
                .build();
    }
}
