package com.codea.backend.entitys;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Column(nullable = false)
    private String nameUser;

    private String lastnameUser;

    @Column(nullable = false, unique = true)
    private String emailUser;

    @Column(nullable = false)
    private String passwordUser;

    private String rolUser;

    private String statusUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_algorithm", nullable = true)
    private Algorithm algorithm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_style", nullable = true)
    private StyleLearning styleLearning;
}
