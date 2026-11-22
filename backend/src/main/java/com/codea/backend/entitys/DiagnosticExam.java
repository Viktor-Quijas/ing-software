package com.codea.backend.entitys;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "diagnostic_exam")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosticExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idExam;

    @Column(nullable = false)
    private String nameExam;

    private String descriptionExam;

    private String statusExam;
}
