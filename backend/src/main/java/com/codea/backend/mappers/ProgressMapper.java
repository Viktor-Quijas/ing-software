package com.codea.backend.mappers;

import org.springframework.stereotype.Component;

import com.codea.backend.dtos.ProgressDTO;
import com.codea.backend.entitys.Activity;
import com.codea.backend.entitys.Progress;
import com.codea.backend.entitys.User;

@Component
public class ProgressMapper {

    public ProgressDTO toDTO(Progress progress) {
        return new ProgressDTO(
                progress.getIdProgress(),
                progress.getStatusProgress(),
                progress.getGradeProgress(),
                progress.getTriesProgress(),
                progress.getDateProgress());
    }

    public Progress toEntity(ProgressDTO dto, User user, Activity activity) {
        Progress progress = new Progress();
        updateEntity(dto, progress, user, activity);
        return progress;
    }

    public void updateEntity(ProgressDTO dto, Progress progress, User user, Activity activity) {
        progress.setUser(user);
        progress.setActivity(activity);
        progress.setStatusProgress(dto.statusProgress());
        progress.setGradeProgress(dto.gradeProgress());
        progress.setTriesProgress(dto.triesProgress());
        progress.setDateProgress(dto.dateProgress());
    }
}