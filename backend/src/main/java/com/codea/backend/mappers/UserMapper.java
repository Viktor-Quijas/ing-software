package com.codea.backend.mappers;

import org.springframework.stereotype.Component;

import com.codea.backend.dtos.UserDTO;
import com.codea.backend.entitys.Algorithm;
import com.codea.backend.entitys.StyleLearning;
import com.codea.backend.entitys.User;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getIdUser(),
                user.getNameUser(),
                user.getLastnameUser(),
                user.getEmailUser(), null,
                user.getRolUser(),
                user.getStatusUser(),
                user.getAlgorithm() != null ? user.getAlgorithm().getIdAlgorithm() : null,
                user.getStyleLearning() != null ? user.getStyleLearning().getIdStyle() : null);
    }

    public User toEntity(UserDTO dto, Algorithm algorithm, StyleLearning styleLearning) {
        User user = new User();
        user.setPasswordUser(dto.passwordUser());
        updateEntity(dto, user, algorithm, styleLearning);
        return user;
    }

    public void updateEntity(UserDTO dto, User user, Algorithm algorithm, StyleLearning styleLearning) {
        user.setNameUser(dto.nameUser());
        user.setLastnameUser(dto.lastnameUser());
        user.setEmailUser(dto.emailUser());
        user.setRolUser(dto.rolUser());
        user.setStatusUser(dto.statusUser());
        user.setAlgorithm(algorithm);
        user.setStyleLearning(styleLearning);
    }
}