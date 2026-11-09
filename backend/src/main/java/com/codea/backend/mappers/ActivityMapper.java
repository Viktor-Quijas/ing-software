package com.codea.backend.mappers;

import org.springframework.stereotype.Component;

import com.codea.backend.dtos.ActivityDTO;
import com.codea.backend.entitys.Activity;
import com.codea.backend.entitys.Lesson;

@Component
public class ActivityMapper {

    public ActivityDTO toDTO(Activity activity) {
        return new ActivityDTO(
                activity.getIdActivity(),
                activity.getLesson() != null ? activity.getLesson().getIdLesson() : null,
                activity.getTitleActivity(),
                activity.getDescriptionActivity(),
                activity.getTypeActivity(),
                activity.getInstructionActivity(),
                activity.getContentActivity(),
                activity.getDifficultyActivity(),
                activity.getLengthActivity());
    }

    public Activity toEntity(ActivityDTO dto, Lesson lesson) {
        Activity activity = new Activity();
        updateEntity(dto, activity, lesson);
        return activity;
    }

    public void updateEntity(ActivityDTO dto, Activity activity, Lesson lesson) {
        activity.setLesson(lesson);
        activity.setTitleActivity(dto.titleActivity());
        activity.setDescriptionActivity(dto.descriptionActivity());
        activity.setTypeActivity(dto.typeActivity());
        activity.setInstructionActivity(dto.instructionActivity());
        activity.setContentActivity(dto.contentActivity());
        activity.setDifficultyActivity(dto.difficultyActivity());
        activity.setLengthActivity(dto.lengthActivity());
    }
}