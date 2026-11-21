package com.codea.backend.repositorys;

import com.codea.backend.entitys.ActivityAttemptAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityAttemptAnswerRepository extends JpaRepository<ActivityAttemptAnswer, Long> {

    List<ActivityAttemptAnswer> findByActivityAttempt_IdActivityAttempt(Long idActivityAttempt);

    List<ActivityAttemptAnswer> findByActivityQuestion_IdActivityQuestion(Long idActivityQuestion);

    List<ActivityAttemptAnswer> findByActivityOption_IdActivityOption(Long idActivityOption);
}
