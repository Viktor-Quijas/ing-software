package com.codea.backend.entitys;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "diagnostic_attempt")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosticAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAttempt;

    private Double scoreAttempt;

    private LocalDate dateAttempt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_exam", nullable = false)
    private DiagnosticExam exam;
}
