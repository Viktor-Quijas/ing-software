package com.codea.backend.repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codea.backend.entitys.StyleLearning;

public interface StyleLearningRepository extends JpaRepository<StyleLearning, Long> {

    Optional<StyleLearning> findByNameStyle(String nameStyle);
}