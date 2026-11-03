package com.codea.backend.repositorys;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codea.backend.entitys.Progress;

public interface ProgressRepository extends JpaRepository<Progress, Long> {

    List<Progress> findByUser_IdUser(Long idUser);

    Optional<Progress> findByUser_IdUserAndActivity_IdActivity(Long idUser, Long idActivity);
}