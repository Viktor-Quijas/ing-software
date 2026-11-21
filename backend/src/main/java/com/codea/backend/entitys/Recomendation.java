package com.codea.backend.entitys;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "recomendation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recomendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRecomendation;

    private String purposeRecomendation;

    private String descriptionRecomendation;

    private LocalDate dateRecomendation;

    private String statusRecomendation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_algorithm", nullable = true)
    private Algorithm algorithm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lesson", nullable = false)
    private Lesson lesson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_activity", nullable = true)
    private Activity activity;
}
