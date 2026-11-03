package com.codea.backend.repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codea.backend.entitys.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailUser(String emailUser);

    boolean existsByEmailUser(String emailUser);
}