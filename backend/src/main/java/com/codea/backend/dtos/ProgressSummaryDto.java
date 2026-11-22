package com.codea.backend.dtos;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgressSummaryDto {

    private String userName;
    private int totalLessons;
    private int completedLessons;
    private int inProgressLessons;
    /** Suma de los minutos de las lecciones completadas. */
    private int completedMinutes;
    /** Promedio de aciertos (0-100) en los cuestionarios; null si aún no hay intentos. */
    private Integer averageScorePercent;
    /** Última lección con la que trabajó el usuario; null si no ha abierto ninguna. */
    private LastLesson lastLesson;
    /** Lunes a domingo de la semana actual. */
    private List<DayMinutes> weekly;
    /** 0 = lunes ... 6 = domingo. */
    private int todayIndex;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LastLesson {
        private Long idLesson;
        private String titleLesson;
        private String difficultyLesson;
        private Integer lengthLesson;
        private int position;
        private String statusProgress;
        private int triesProgress;
        private Double gradeProgress;
        private int totalQuestions;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DayMinutes {
        private String day;
        private int minutes;
    }
}