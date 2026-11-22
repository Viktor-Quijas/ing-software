package com.codea.backend.entitys;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "question")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idQuestion;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String textQuestion;

    private String topicQuestion;

    private String difficultyQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_exam", nullable = false)
    private DiagnosticExam exam;
}
