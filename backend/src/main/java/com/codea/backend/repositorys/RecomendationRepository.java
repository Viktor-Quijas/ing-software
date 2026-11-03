package com.codea.backend.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codea.backend.entitys.Recomendation;

public interface RecomendationRepository extends JpaRepository<Recomendation, Long> {

    List<Recomendation> findByUser_IdUser(Long idUser);

    List<Recomendation> findByUser_IdUserAndStatusRecomendation(Long idUser, String statusRecomendation);
}