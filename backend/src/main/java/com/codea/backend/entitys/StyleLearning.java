package com.codea.backend.entitys;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "style_learning")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StyleLearning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStyle;

    @Column(nullable = false)
    private String nameStyle;

    private String descriptionStyle;
}
