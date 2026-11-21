package com.codea.backend.entitys;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "options")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOption;

    @Column(nullable = false)
    private String textOption;

    private Boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_question", nullable = false)
    private Question question;
}
