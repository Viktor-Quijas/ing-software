package com.codea.backend.entitys;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "algorithm")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Algorithm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlgorithm;

    @Column(nullable = false)
    private String nameAlgorithm;

    private String descriptionAlgorithm;

    private String versionAlgorithm;

    private String statusAlgorithm;
}
