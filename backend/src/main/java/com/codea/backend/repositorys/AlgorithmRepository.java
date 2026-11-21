package com.codea.backend.repositorys;

import com.codea.backend.entitys.Algorithm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlgorithmRepository extends JpaRepository<Algorithm, Long> {

    Optional<Algorithm> findFirstByOrderByIdAlgorithmAsc();
}
