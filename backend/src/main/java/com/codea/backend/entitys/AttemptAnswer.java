package com.codea.backend.entitys;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "attempt_answer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttemptAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAnswer;

    private Boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_attempt", nullable = false)
    private DiagnosticAttempt attempt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_question", nullable = false)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_option", nullable = false)
    private Option option;
}
