package com.codea.backend.repositorys;

import com.codea.backend.entitys.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Optional<Activity> findByLesson_IdLesson(Long idLesson);
}
