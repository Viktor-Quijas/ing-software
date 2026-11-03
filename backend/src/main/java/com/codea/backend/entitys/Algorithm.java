package com.codea.backend.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "algorithm")
public class Algorithm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Algorithm")
    private Long idAlgorithm;

    @Column(name = "nameAlgorithm", nullable = false)
    private String nameAlgorithm;

    @Column(name = "descriptionAlgorithm", columnDefinition = "TEXT")
    private String descriptionAlgorithm;

    @Column(name = "versionAlgorithm")
    private String versionAlgorithm;

    @Column(name = "statusAlgorithm")
    private String statusAlgorithm;


    public Long getIdAlgorithm() {
        return idAlgorithm;
    }

    public void setIdAlgorithm(Long idAlgorithm) {
        this.idAlgorithm = idAlgorithm;
    }

    public String getNameAlgorithm() {
        return nameAlgorithm;
    }

    public void setNameAlgorithm(String nameAlgorithm) {
        this.nameAlgorithm = nameAlgorithm;
    }

    public String getDescriptionAlgorithm() {
        return descriptionAlgorithm;
    }

    public void setDescriptionAlgorithm(String descriptionAlgorithm) {
        this.descriptionAlgorithm = descriptionAlgorithm;
    }

    public String getVersionAlgorithm() {
        return versionAlgorithm;
    }

    public void setVersionAlgorithm(String versionAlgorithm) {
        this.versionAlgorithm = versionAlgorithm;
    }

    public String getStatusAlgorithm() {
        return statusAlgorithm;
    }

    public void setStatusAlgorithm(String statusAlgorithm) {
        this.statusAlgorithm = statusAlgorithm;
    }
}