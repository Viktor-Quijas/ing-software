package com.codea.backend.repositorys;

import com.codea.backend.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByAlgorithm_IdAlgorithm(Long idAlgorithm);

    List<User> findByStyleLearning_IdStyle(Long idStyle);

    Optional<User> findByEmailUser(String emailUser);

    boolean existsByEmailUser(String emailUser);
}
