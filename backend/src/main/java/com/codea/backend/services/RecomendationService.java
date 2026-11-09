package com.codea.backend.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.codea.backend.dtos.RecomendationDTO;
import com.codea.backend.entitys.Activity;
import com.codea.backend.entitys.Algorithm;
import com.codea.backend.entitys.Lesson;
import com.codea.backend.entitys.Recomendation;
import com.codea.backend.entitys.User;
import com.codea.backend.mappers.RecomendationMapper;
import com.codea.backend.repositorys.ActivityRepository;
import com.codea.backend.repositorys.AlgorithmRepository;
import com.codea.backend.repositorys.LessonRepository;
import com.codea.backend.repositorys.RecomendationRepository;
import com.codea.backend.repositorys.UserRepository;

@Service
public class RecomendationService {

    private final RecomendationRepository recomendationRepository;
    private final UserRepository userRepository;
    private final AlgorithmRepository algorithmRepository;
    private final LessonRepository lessonRepository;
    private final ActivityRepository activityRepository;
    private final RecomendationMapper recomendationMapper;

    public RecomendationService(RecomendationRepository recomendationRepository, UserRepository userRepository,
                                AlgorithmRepository algorithmRepository, LessonRepository lessonRepository,
                                ActivityRepository activityRepository, RecomendationMapper recomendationMapper) {
        this.recomendationRepository = recomendationRepository;
        this.userRepository = userRepository;
        this.algorithmRepository = algorithmRepository;
        this.lessonRepository = lessonRepository;
        this.activityRepository = activityRepository;
        this.recomendationMapper = recomendationMapper;
    }

    public List<RecomendationDTO> findByUser(Long idUser) {
        return recomendationRepository.findByUser_IdUser(idUser).stream()
                .map(recomendationMapper::toDTO).toList();
    }

    public RecomendationDTO findById(Long id) {
        return recomendationMapper.toDTO(getRecomendation(id));
    }

    public RecomendationDTO create(RecomendationDTO dto, Long idAlgorithm, Long idLesson, Long idActivity) {
        User user = userRepository.findById(dto.idUser()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con id " + dto.idUser()));

        // Si no se indica algoritmo, usa el asignado al usuario
        Algorithm algorithm = idAlgorithm != null
                ? algorithmRepository.findById(idAlgorithm).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Algoritmo no encontrado con id " + idAlgorithm))
                : user.getAlgorithm();
        if (algorithm == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Indica un idAlgorithm o asigna un algoritmo al usuario");
        }

        Lesson lesson = idLesson == null ? null : lessonRepository.findById(idLesson).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Lección no encontrada con id " + idLesson));
        Activity activity = idActivity == null ? null : activityRepository.findById(idActivity).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Actividad no encontrada con id " + idActivity));

        Recomendation recomendation = recomendationMapper.toEntity(dto, user, algorithm, lesson, activity);
        return recomendationMapper.toDTO(recomendationRepository.save(recomendation));
    }

    public RecomendationDTO update(Long id, RecomendationDTO dto) {
        Recomendation r = getRecomendation(id);
        // conserva las relaciones originales, solo cambia los datos propios
        recomendationMapper.updateEntity(dto, r, r.getUser(), r.getAlgorithm(), r.getLesson(), r.getActivity());
        return recomendationMapper.toDTO(recomendationRepository.save(r));
    }

    public void delete(Long id) {
        getRecomendation(id);
        recomendationRepository.deleteById(id);
    }

    private Recomendation getRecomendation(Long id) {
        return recomendationRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Recomendación no encontrada con id " + id));
    }
}