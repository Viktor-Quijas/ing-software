package com.codea.backend.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "styleLearning")
public class StyleLearning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK
    @Column(name = "id_Style")
    private Long idStyle;

    @Column(name = "nameStyle", nullable = false)
    private String nameStyle;

    @Column(name = "descriptionStyle", columnDefinition = "TEXT")
    private String descriptionStyle;


    public Long getIdStyle() {
        return idStyle;
    }

    public void setIdStyle(Long idStyle) {
        this.idStyle = idStyle;
    }

    public String getNameStyle() {
        return nameStyle;
    }

    public void setNameStyle(String nameStyle) {
        this.nameStyle = nameStyle;
    }

    public String getDescriptionStyle() {
        return descriptionStyle;
    }

    public void setDescriptionStyle(String descriptionStyle) {
        this.descriptionStyle = descriptionStyle;
    }
}