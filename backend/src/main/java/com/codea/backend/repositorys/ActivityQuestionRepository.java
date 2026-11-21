package com.codea.backend.repositorys;

import com.codea.backend.entitys.ActivityQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityQuestionRepository extends JpaRepository<ActivityQuestion, Long> {

    List<ActivityQuestion> findByActivity_IdActivity(Long idActivity);
}
