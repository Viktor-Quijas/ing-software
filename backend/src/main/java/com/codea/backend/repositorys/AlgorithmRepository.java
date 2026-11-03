package com.codea.backend.repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codea.backend.entitys.Algorithm;

public interface AlgorithmRepository extends JpaRepository<Algorithm, Long> {

    Optional<Algorithm> findByNameAlgorithm(String nameAlgorithm);
}