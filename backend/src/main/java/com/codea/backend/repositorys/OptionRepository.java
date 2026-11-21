package com.codea.backend.repositorys;

import com.codea.backend.entitys.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {

    List<Option> findByQuestion_IdQuestion(Long idQuestion);
}
