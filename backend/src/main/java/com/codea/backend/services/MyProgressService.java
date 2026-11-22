package com.codea.backend.services;

import com.codea.backend.dtos.ProgressSummaryDto;
import com.codea.backend.entitys.Activity;
import com.codea.backend.entitys.Lesson;
import com.codea.backend.entitys.Progress;
import com.codea.backend.entitys.User;
import com.codea.backend.exceptions.ResourceNotFoundException;
import com.codea.backend.repositorys.ActivityQuestionRepository;
import com.codea.backend.repositorys.ActivityRepository;
import com.codea.backend.repositorys.LessonRepository;
import com.codea.backend.repositorys.ProgressRepository;
import com.codea.backend.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Avances del usuario autenticado. */
@Service
@RequiredArgsConstructor
public class MyProgressService {

    private static final String COMPLETED = "COMPLETADO";
    private static final String IN_PROGRESS = "EN_PROGRESO";
    private static final String[] DAYS = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"};

    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final ActivityRepository activityRepository;
    private final ActivityQuestionRepository questionRepository;
    private final ProgressRepository progressRepository;

    @Transactional(readOnly = true)
    public ProgressSummaryDto summary(String email) {
        User user = getUser(email);

        List<Lesson> lessons = lessonRepository.findAll().stream()
                .sorted(Comparator.comparing(Lesson::getIdLesson))
                .toList();
        Map<Long, Integer> position = new HashMap<>();
        for (int i = 0; i < lessons.size(); i++) {
            position.put(lessons.get(i).getIdLesson(), i + 1);
        }

        List<Progress> mine = progressRepository.findByUser_IdUser(user.getIdUser());

        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(DayOfWeek.MONDAY);
        int[] weekly = new int[7];
        int completed = 0;
        int inProgress = 0;
        int completedMinutes = 0;
        double percentSum = 0;
        int percentCount = 0;

        for (Progress p : mine) {
            Lesson lesson = p.getActivity().getLesson();
            int minutes = lesson.getLengthLesson() == null ? 0 : lesson.getLengthLesson();

            if (COMPLETED.equalsIgnoreCase(p.getStatusProgress())) {
                completed++;
                completedMinutes += minutes;
            } else {
                inProgress++;
            }

            if (p.getTriesProgress() != null && p.getTriesProgress() > 0 && p.getGradeProgress() != null) {
                int total = questionRepository.findByActivity_IdActivity(p.getActivity().getIdActivity()).size();
                if (total > 0) {
                    percentSum += p.getGradeProgress() * 100.0 / total;
                    percentCount++;
                }
            }

            long offset = ChronoUnit.DAYS.between(monday, accessedAt(p).toLocalDate());
            if (offset >= 0 && offset < 7) {
                weekly[(int) offset] += minutes;
            }
        }

        Comparator<Progress> byRecent = Comparator.comparing(this::accessedAt);
        Progress last = mine.stream()
                .max(byRecent.thenComparing(Progress::getIdProgress))
                .orElse(null);

        ProgressSummaryDto.LastLesson lastLesson = null;
        if (last != null) {
            Activity activity = last.getActivity();
            Lesson lesson = activity.getLesson();
            lastLesson = ProgressSummaryDto.LastLesson.builder()
                    .idLesson(lesson.getIdLesson())
                    .titleLesson(lesson.getTitleLesson())
                    .difficultyLesson(lesson.getDifficultyLesson())
                    .lengthLesson(lesson.getLengthLesson())
                    .position(position.getOrDefault(lesson.getIdLesson(), 0))
                    .statusProgress(last.getStatusProgress())
                    .triesProgress(last.getTriesProgress() == null ? 0 : last.getTriesProgress())
                    .gradeProgress(last.getGradeProgress())
                    .totalQuestions(questionRepository.findByActivity_IdActivity(activity.getIdActivity()).size())
                    .build();
        }

        List<ProgressSummaryDto.DayMinutes> week = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            week.add(new ProgressSummaryDto.DayMinutes(DAYS[i], weekly[i]));
        }

        return ProgressSummaryDto.builder()
                .userName(user.getNameUser())
                .totalLessons(lessons.size())
                .completedLessons(completed)
                .inProgressLessons(inProgress)
                .completedMinutes(completedMinutes)
                .averageScorePercent(percentCount == 0 ? null : (int) Math.round(percentSum / percentCount))
                .lastLesson(lastLesson)
                .weekly(week)
                .todayIndex(today.getDayOfWeek().getValue() - 1)
                .build();
    }

    /** Marca la lección como la última abierta (crea el progreso EN_PROGRESO si no existía). */
    @Transactional
    public void startLesson(String email, Long idLesson) {
        User user = getUser(email);
        Activity activity = activityRepository.findByLesson_IdLesson(idLesson)
                .orElseThrow(() -> new ResourceNotFoundException("Lección sin actividad o inexistente: " + idLesson));

        Progress progress = progressRepository
                .findByUser_IdUserAndActivity_IdActivity(user.getIdUser(), activity.getIdActivity())
                .orElseGet(() -> Progress.builder()
                        .user(user)
                        .activity(activity)
                        .statusProgress(IN_PROGRESS)
                        .triesProgress(0)
                        .gradeProgress(0.0)
                        .build());

        progress.setDateProgress(LocalDate.now());
        progress.setLastAccessProgress(LocalDateTime.now());
        progressRepository.save(progress);
    }

    private User getUser(String email) {
        return userRepository.findByEmailUser(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + email));
    }

    private LocalDateTime accessedAt(Progress p) {
        if (p.getLastAccessProgress() != null) return p.getLastAccessProgress();
        if (p.getDateProgress() != null) return p.getDateProgress().atStartOfDay();
        return LocalDateTime.of(1970, 1, 1, 0, 0);
    }
}