package com.codea.backend.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.codea.backend.dtos.ActivityDTO;
import com.codea.backend.entitys.Activity;
import com.codea.backend.entitys.Lesson;
import com.codea.backend.mappers.ActivityMapper;
import com.codea.backend.repositorys.ActivityRepository;
import com.codea.backend.repositorys.LessonRepository;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final LessonRepository lessonRepository;
    private final ActivityMapper activityMapper;

    public ActivityService(ActivityRepository activityRepository, LessonRepository lessonRepository, ActivityMapper activityMapper) {
        this.activityRepository = activityRepository;
        this.lessonRepository = lessonRepository;
        this.activityMapper = activityMapper;
    }

    public List<ActivityDTO> findAll() {
        return activityRepository.findAll().stream().map(activityMapper::toDTO).toList();
    }

    public List<ActivityDTO> findByLesson(Long idLesson) {
        return activityRepository.findByLesson_IdLesson(idLesson).stream().map(activityMapper::toDTO).toList();
    }

    public ActivityDTO findById(Long id) {
        return activityMapper.toDTO(getActivity(id));
    }

    public ActivityDTO create(ActivityDTO dto) {
        Activity activity = activityMapper.toEntity(dto, getLesson(dto.idLesson()));
        return activityMapper.toDTO(activityRepository.save(activity));
    }

    public ActivityDTO update(Long id, ActivityDTO dto) {
        Activity activity = getActivity(id);
        activityMapper.updateEntity(dto, activity, getLesson(dto.idLesson()));
        return activityMapper.toDTO(activityRepository.save(activity));
    }

    public void delete(Long id) {
        getActivity(id);
        activityRepository.deleteById(id);
    }

    private Activity getActivity(Long id) {
        return activityRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Actividad no encontrada con id " + id));
    }

    private Lesson getLesson(Long idLesson) {
        if (idLesson == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "idLesson es obligatorio");
        }
        return lessonRepository.findById(idLesson).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Lección no encontrada con id " + idLesson));
    }
}