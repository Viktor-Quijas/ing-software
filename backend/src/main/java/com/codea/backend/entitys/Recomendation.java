package com.codea.backend.entitys;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "recomendation")
public class Recomendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK
    @Column(name = "id_Recomendation")
    private Long idRecomendation;

    @ManyToOne
    @JoinColumn(name = "id_User") //FK
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_Algorithm") //FK
    private Algorithm algorithm;

    @ManyToOne
    @JoinColumn(name = "id_Lesson") //FK
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "id_Activity") //FK
    private Activity activity;

    @Column(name = "purposeRecomendation")
    private String purposeRecomendation;

    @Column(name = "dateRecomendation") 
    private LocalDate dateRecomendation;

    @Column(name = "statusRecomendation")
    private String statusRecomendation;


    public Long getIdRecomendation() {
        return idRecomendation;
    }

    public void setIdRecomendation(Long idRecomendation) {
        this.idRecomendation = idRecomendation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getPurposeRecomendation() {
        return purposeRecomendation;
    }

    public void setPurposeRecomendation(String purposeRecomendation) {
        this.purposeRecomendation = purposeRecomendation;
    }

    public LocalDate getDateRecomendation() {
        return dateRecomendation;
    }

    public void setDateRecomendation(LocalDate dateRecomendation) {
        this.dateRecomendation = dateRecomendation;
    }

    public String getStatusRecomendation() {
        return statusRecomendation;
    }

    public void setStatusRecomendation(String statusRecomendation) {
        this.statusRecomendation = statusRecomendation;
    }
}