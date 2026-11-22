package com.codea.backend.repositorys;

import com.codea.backend.entitys.StyleLearning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StyleLearningRepository extends JpaRepository<StyleLearning, Long> {

    Optional<StyleLearning> findByNameStyleIgnoreCase(String nameStyle);
}
