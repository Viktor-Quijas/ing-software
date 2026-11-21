package com.codea.backend.entitys;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lesson")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLesson;

    @Column(nullable = false)
    private String titleLesson;

    private String authorLesson;

    @Column(columnDefinition = "TEXT")
    private String contentLesson;

    private Integer lengthLesson;

    private String difficultyLesson;

    private String statusLesson;
}
