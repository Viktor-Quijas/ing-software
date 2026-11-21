package com.codea.backend.entitys;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "activity_option")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idActivityOption;

    @Column(nullable = false)
    private String textOption;

    private Boolean isCorrect;

    private Integer orderOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_activity_question", nullable = false)
    private ActivityQuestion activityQuestion;
}
