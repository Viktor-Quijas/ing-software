package com.codea.backend.repositorys;

import com.codea.backend.entitys.ActivityAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityAttemptRepository extends JpaRepository<ActivityAttempt, Long> {

    List<ActivityAttempt> findByUser_IdUser(Long idUser);

    List<ActivityAttempt> findByActivity_IdActivity(Long idActivity);
}
