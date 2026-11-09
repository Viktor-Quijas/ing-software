package com.codea.backend.dtos;

public record LessonDTO (
    Long idLesson,
    String titleLesson,
    String authorLesson,
    String contentLesson,
    Integer lengthLesson,
    String difficultyLesson
){
    
}
