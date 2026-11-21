package com.codea.backend.mappers;

import com.codea.backend.dtos.UserRequestDto;
import com.codea.backend.dtos.UserResponseDto;
import com.codea.backend.entitys.Algorithm;
import com.codea.backend.entitys.StyleLearning;
import com.codea.backend.entitys.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDto toResponse(User u) {
        if (u == null) return null;
        return UserResponseDto.builder()
                .idUser(u.getIdUser())
                .nameUser(u.getNameUser())
                .lastnameUser(u.getLastnameUser())
                .emailUser(u.getEmailUser())
                .rolUser(u.getRolUser())
                .statusUser(u.getStatusUser())
                .idAlgorithm(u.getAlgorithm() == null ? null : u.getAlgorithm().getIdAlgorithm())
                .idStyle(u.getStyleLearning() == null ? null : u.getStyleLearning().getIdStyle())
                .styleName(u.getStyleLearning() == null ? null : u.getStyleLearning().getNameStyle())
                .build();
    }

    /** No copia la contraseña: la encripta el servicio. */
    public User toEntity(UserRequestDto d) {
        User u = new User();
        updateEntity(u, d);
        return u;
    }

    public void updateEntity(User u, UserRequestDto d) {
        u.setNameUser(d.getNameUser());
        u.setLastnameUser(d.getLastnameUser());
        u.setEmailUser(d.getEmailUser());
        u.setRolUser(d.getRolUser());
        u.setStatusUser(d.getStatusUser());
        u.setAlgorithm(d.getIdAlgorithm() == null ? null : refAlgorithm(d.getIdAlgorithm()));
        u.setStyleLearning(d.getIdStyle() == null ? null : refStyle(d.getIdStyle()));
    }

    private Algorithm refAlgorithm(Long id) {
        Algorithm a = new Algorithm();
        a.setIdAlgorithm(id);
        return a;
    }

    private StyleLearning refStyle(Long id) {
        StyleLearning s = new StyleLearning();
        s.setIdStyle(id);
        return s;
    }
}
