package com.codea.backend.entitys;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "activity_question")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idActivityQuestion;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String textQuestion;

    private String topicQuestion;

    private String difficultyQuestion;

    private Integer orderQuestion;

    private String typeQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_activity", nullable = false)
    private Activity activity;
}
