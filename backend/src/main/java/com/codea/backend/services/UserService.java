package com.codea.backend.services;

import com.codea.backend.dtos.UserRequestDto;
import com.codea.backend.dtos.UserResponseDto;
import com.codea.backend.entitys.User;
import com.codea.backend.exceptions.BadRequestException;
import com.codea.backend.exceptions.ResourceNotFoundException;
import com.codea.backend.mappers.UserMapper;
import com.codea.backend.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) {
        return mapper.toResponse(getEntity(id));
    }

    public UserResponseDto create(UserRequestDto dto) {
        if (dto.getPasswordUser() == null || dto.getPasswordUser().isBlank()) {
            throw new BadRequestException("La contraseña es obligatoria");
        }
        if (repository.existsByEmailUser(dto.getEmailUser())) {
            throw new BadRequestException("Ya existe un usuario con ese correo");
        }
        User user = mapper.toEntity(dto);
        user.setPasswordUser(passwordEncoder.encode(dto.getPasswordUser()));
        if (user.getRolUser() == null || user.getRolUser().isBlank()) user.setRolUser("USER");
        if (user.getStatusUser() == null || user.getStatusUser().isBlank()) user.setStatusUser("ACTIVO");
        return mapper.toResponse(repository.saveAndFlush(user));
    }

    public UserResponseDto update(Long id, UserRequestDto dto) {
        User user = getEntity(id);
        if (!user.getEmailUser().equalsIgnoreCase(dto.getEmailUser())
                && repository.existsByEmailUser(dto.getEmailUser())) {
            throw new BadRequestException("Ya existe un usuario con ese correo");
        }
        mapper.updateEntity(user, dto);
        if (dto.getPasswordUser() != null && !dto.getPasswordUser().isBlank()) {
            user.setPasswordUser(passwordEncoder.encode(dto.getPasswordUser()));
        }
        return mapper.toResponse(repository.saveAndFlush(user));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado: " + id);
        }
        repository.deleteById(id);
        repository.flush();
    }

    @Transactional(readOnly = true)
    public User getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + id));
    }
}
