package com.codea.backend.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK
    @Column(name = "id_User")
    private Long idUser;

    @Column(name = "nameUser", nullable = false)
    private String nameUser;

    @Column(name = "lastnameUser", nullable = false)
    private String lastnameUser;

    @Column(name = "emailUser", nullable = false, unique = true) 
    private String emailUser;

    @Column(name = "passwordUser", nullable = false)
    private String passwordUser;

    @Column(name = "rolUser")
    private String rolUser;

    @Column(name = "statusUser")
    private String statusUser;

    @ManyToOne
    @JoinColumn(name = "id_Algorithm") //FK
    private Algorithm algorithm;

    @ManyToOne
    @JoinColumn(name = "id_Style") //FK
    private StyleLearning styleLearning;


    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getLastnameUser() {
        return lastnameUser;
    }

    public void setLastnameUser(String lastnameUser) {
        this.lastnameUser = lastnameUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public String getRolUser() {
        return rolUser;
    }

    public void setRolUser(String rolUser) {
        this.rolUser = rolUser;
    }

    public String getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(String statusUser) {
        this.statusUser = statusUser;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public StyleLearning getStyleLearning() {
        return styleLearning;
    }

    public void setStyleLearning(StyleLearning styleLearning) {
        this.styleLearning = styleLearning;
    }
}