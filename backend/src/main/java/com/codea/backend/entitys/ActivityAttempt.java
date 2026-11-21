package com.codea.backend.entitys;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "activity_attempt")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idActivityAttempt;

    private Double scoreAttempt;

    private LocalDate dateAttempt;

    private String statusAttempt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_activity", nullable = false)
    private Activity activity;
}
