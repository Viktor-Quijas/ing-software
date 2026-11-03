package com.codea.backend.entitys;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "lesson")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Lesson")
    private Long idLesson;

    @Column(name = "titleLesson", nullable = false)
    private String titleLesson;

    @Column(name = "authorLesson")
    private String authorLesson;

    @Column(name = "contentLesson", columnDefinition = "TEXT")
    private String contentLesson;

    @Column(name = "lengthLesson")
    private Integer lengthLesson;

    @Column(name = "difficultyLesson")
    private String difficultyLesson;

    @OneToMany(mappedBy = "lesson")
    private List<Activity> activities = new ArrayList<>();


    public Long getIdLesson() {
        return idLesson;
    }

    public void setIdLesson(Long idLesson) {
        this.idLesson = idLesson;
    }

    public String getTitleLesson() {
        return titleLesson;
    }

    public void setTitleLesson(String titleLesson) {
        this.titleLesson = titleLesson;
    }

    public String getAuthorLesson() {
        return authorLesson;
    }

    public void setAuthorLesson(String authorLesson) {
        this.authorLesson = authorLesson;
    }

    public String getContentLesson() {
        return contentLesson;
    }

    public void setContentLesson(String contentLesson) {
        this.contentLesson = contentLesson;
    }

    public Integer getLengthLesson() {
        return lengthLesson;
    }

    public void setLengthLesson(Integer lengthLesson) {
        this.lengthLesson = lengthLesson;
    }

    public String getDifficultyLesson() {
        return difficultyLesson;
    }

    public void setDifficultyLesson(String difficultyLesson) {
        this.difficultyLesson = difficultyLesson;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}