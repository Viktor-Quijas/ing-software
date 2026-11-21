package com.codea.backend.entitys;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "activity_attempt_answer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityAttemptAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAnswer;

    private Boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_activity_attempt", nullable = false)
    private ActivityAttempt activityAttempt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_activity_question", nullable = false)
    private ActivityQuestion activityQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_activity_option", nullable = false)
    private ActivityOption activityOption;
}
