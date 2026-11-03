package com.codea.backend.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codea.backend.entitys.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findByLesson_IdLesson(Long idLesson);

    List<Activity> findByDifficultyActivity(String difficultyActivity);
}
