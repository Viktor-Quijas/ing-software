package com.codea.backend.mappers;

import org.springframework.stereotype.Component;

import com.codea.backend.dtos.LessonDTO;
import com.codea.backend.entitys.Lesson;

@Component
public class LessonMapper {

    public LessonDTO toDTO(Lesson lesson) {
        return new LessonDTO(
                lesson.getIdLesson(),
                lesson.getTitleLesson(),
                lesson.getAuthorLesson(),
                lesson.getContentLesson(),
                lesson.getLengthLesson(),
                lesson.getDifficultyLesson());
    }

    public Lesson toEntity(LessonDTO dto) {
        Lesson lesson = new Lesson();
        updateEntity(dto, lesson);
        return lesson;
    }

    public void updateEntity(LessonDTO dto, Lesson lesson) {
        lesson.setTitleLesson(dto.titleLesson());
        lesson.setAuthorLesson(dto.authorLesson());
        lesson.setContentLesson(dto.contentLesson());
        lesson.setLengthLesson(dto.lengthLesson());
        lesson.setDifficultyLesson(dto.difficultyLesson());
    }
}