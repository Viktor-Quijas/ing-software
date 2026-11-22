package com.codea.backend.repositorys;

import com.codea.backend.entitys.ActivityOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityOptionRepository extends JpaRepository<ActivityOption, Long> {

    List<ActivityOption> findByActivityQuestion_IdActivityQuestion(Long idActivityQuestion);
}
