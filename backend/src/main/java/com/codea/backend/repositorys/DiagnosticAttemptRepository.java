package com.codea.backend.repositorys;

import com.codea.backend.entitys.DiagnosticAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiagnosticAttemptRepository extends JpaRepository<DiagnosticAttempt, Long> {

    List<DiagnosticAttempt> findByUser_IdUser(Long idUser);

    List<DiagnosticAttempt> findByExam_IdExam(Long idExam);
}
