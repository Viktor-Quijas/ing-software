package com.codea.backend.entitys;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "progress", uniqueConstraints = @UniqueConstraint(columnNames = {"id_user", "id_activity"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProgress;

    private String statusProgress;

    private Double gradeProgress;

    private Integer triesProgress;

    private LocalDate dateProgress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_activity", nullable = false)
    private Activity activity;
}
