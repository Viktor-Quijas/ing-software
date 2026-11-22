package com.codea.backend.repositorys;

import com.codea.backend.entitys.Recomendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecomendationRepository extends JpaRepository<Recomendation, Long> {

    List<Recomendation> findByUser_IdUser(Long idUser);

    List<Recomendation> findByUser_IdUserOrderByIdRecomendationDesc(Long idUser);

    List<Recomendation> findByAlgorithm_IdAlgorithm(Long idAlgorithm);

    List<Recomendation> findByLesson_IdLesson(Long idLesson);

    List<Recomendation> findByActivity_IdActivity(Long idActivity);

    boolean existsByUser_IdUserAndLesson_IdLesson(Long idUser, Long idLesson);

    void deleteByUser_IdUserAndLesson_IdLesson(Long idUser, Long idLesson);
}
