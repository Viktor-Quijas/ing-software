package com.codea.backend.entitys;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "activity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idActivity;

    @Column(nullable = false)
    private String titleActivity;

    private String descriptionActivity;

    private String typeActivity;

    @Column(columnDefinition = "TEXT")
    private String instructionActivity;

    private Integer lengthActivity;

    private Double passingScore;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lesson", nullable = false, unique = true)
    private Lesson lesson;
}
