package com.codea.backend.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.codea.backend.dtos.ProgressDTO;
import com.codea.backend.entitys.Activity;
import com.codea.backend.entitys.Progress;
import com.codea.backend.entitys.User;
import com.codea.backend.mappers.ProgressMapper;
import com.codea.backend.repositorys.ActivityRepository;
import com.codea.backend.repositorys.ProgressRepository;
import com.codea.backend.repositorys.UserRepository;

@Service
public class ProgressService {

    private final ProgressRepository progressRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final ProgressMapper progressMapper;

    public ProgressService(ProgressRepository progressRepository, UserRepository userRepository, ActivityRepository activityRepository, ProgressMapper progressMapper) {
        this.progressRepository = progressRepository;
        this.userRepository = userRepository;
        this.activityRepository = activityRepository;
        this.progressMapper = progressMapper;
    }

    public List<ProgressDTO> findByUser(Long idUser) {
        return progressRepository.findByUser_IdUser(idUser).stream().map(progressMapper::toDTO).toList();
    }

    public ProgressDTO findById(Long id) {
        return progressMapper.toDTO(getProgress(id));
    }

    public ProgressDTO create(Long idUser, Long idActivity, ProgressDTO dto) {
        User user = userRepository.findById(idUser).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con id " + idUser));
        Activity activity = activityRepository.findById(idActivity).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Actividad no encontrada con id " + idActivity));
        Progress progress = progressMapper.toEntity(dto, user, activity);
        return progressMapper.toDTO(progressRepository.save(progress));
    }

    public ProgressDTO update(Long id, ProgressDTO dto) {
        Progress progress = getProgress(id);
        // conserva el usuario y la actividad originales
        progressMapper.updateEntity(dto, progress, progress.getUser(), progress.getActivity());
        return progressMapper.toDTO(progressRepository.save(progress));
    }

    public void delete(Long id) {
        getProgress(id);
        progressRepository.deleteById(id);
    }

    private Progress getProgress(Long id) {
        return progressRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Progreso no encontrado con id " + id));
    }
}