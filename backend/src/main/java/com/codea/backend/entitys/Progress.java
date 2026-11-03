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
@Table(name = "progress")
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK
    @Column(name = "id_Progress")
    private Long idProgress;

    @ManyToOne
    @JoinColumn(name = "id_User") //FK
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_Activity") //FK
    private Activity activity;

    @Column(name = "statusProgress")
    private String statusProgress;

    @Column(name = "gradeProgress")
    private Double gradeProgress;

    @Column(name = "triesProgress")
    private Integer triesProgress;

    @Column(name = "dateProgress") 
    private LocalDate dateProgress;


    public Long getIdProgress() {
        return idProgress;
    }

    public void setIdProgress(Long idProgress) {
        this.idProgress = idProgress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getStatusProgress() {
        return statusProgress;
    }

    public void setStatusProgress(String statusProgress) {
        this.statusProgress = statusProgress;
    }

    public Double getGradeProgress() {
        return gradeProgress;
    }

    public void setGradeProgress(Double gradeProgress) {
        this.gradeProgress = gradeProgress;
    }

    public Integer getTriesProgress() {
        return triesProgress;
    }

    public void setTriesProgress(Integer triesProgress) {
        this.triesProgress = triesProgress;
    }

    public LocalDate getDateProgress() {
        return dateProgress;
    }

    public void setDateProgress(LocalDate dateProgress) {
        this.dateProgress = dateProgress;
    }
}