package com.codea.backend.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codea.backend.entitys.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findByDifficultyLesson(String difficultyLesson);

    List<Lesson> findByTitleLessonContainingIgnoreCase(String titleLesson);
}