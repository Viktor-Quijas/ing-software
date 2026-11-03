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
@Table(name = "Activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK
    @Column(name = "id_Activity")
    private Long idActivity;

    @ManyToOne
    @JoinColumn(name = "id_Lesson")  //FK
    private Lesson lesson;

    @Column(name = "titleActivity")
    private String titleActivity;

    @Column(name = "descriptionActivity", columnDefinition = "TEXT")
    private String descriptionActivity;

    @Column(name = "typeActivity")
    private String typeActivity;

    @Column(name = "instructionActivity", columnDefinition = "TEXT")
    private String instructionActivity;

    @Column(name = "contentActivity", columnDefinition = "TEXT")
    private String contentActivity;

    @Column(name = "difficultyActivity")
    private String difficultyActivity;

    @Column(name = "lengthActivity")
    private Integer lengthActivity;


    public Long getIdActivity() {
        return idActivity;
    }

    public void setIdActivity(Long idActivity) {
        this.idActivity = idActivity;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public String getTitleActivity() {
        return titleActivity;
    }

    public void setTitleActivity(String titleActivity) {
        this.titleActivity = titleActivity;
    }

    public String getDescriptionActivity() {
        return descriptionActivity;
    }

    public void setDescriptionActivity(String descriptionActivity) {
        this.descriptionActivity = descriptionActivity;
    }

    public String getTypeActivity() {
        return typeActivity;
    }

    public void setTypeActivity(String typeActivity) {
        this.typeActivity = typeActivity;
    }

    public String getInstructionActivity() {
        return instructionActivity;
    }

    public void setInstructionActivity(String instructionActivity) {
        this.instructionActivity = instructionActivity;
    }

    public String getContentActivity() {
        return contentActivity;
    }

    public void setContentActivity(String contentActivity) {
        this.contentActivity = contentActivity;
    }

    public String getDifficultyActivity() {
        return difficultyActivity;
    }

    public void setDifficultyActivity(String difficultyActivity) {
        this.difficultyActivity = difficultyActivity;
    }

    public Integer getLengthActivity() {
        return lengthActivity;
    }

    public void setLengthActivity(Integer lengthActivity) {
        this.lengthActivity = lengthActivity;
    }
}