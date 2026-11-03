package com.codea.backend.dtos;

public record ActivityDTO (

    Long idActivity,
    Long idLesson,
    String titleActivity,
    String descriptionActivity,
    String typeActivity,
    String instructionActivity,
    String contentActivity,
    String difficultyActivity,
    Integer lengthActivity ){

    }

    

