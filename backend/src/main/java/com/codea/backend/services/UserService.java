package com.codea.backend.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.codea.backend.dtos.UserDTO;
import com.codea.backend.entitys.Algorithm;
import com.codea.backend.entitys.StyleLearning;
import com.codea.backend.entitys.User;
import com.codea.backend.mappers.UserMapper;
import com.codea.backend.repositorys.AlgorithmRepository;
import com.codea.backend.repositorys.StyleLearningRepository;
import com.codea.backend.repositorys.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AlgorithmRepository algorithmRepository;
    private final StyleLearningRepository styleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AlgorithmRepository algorithmRepository,
                       StyleLearningRepository styleRepository, UserMapper userMapper,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.algorithmRepository = algorithmRepository;
        this.styleRepository = styleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(userMapper::toDTO).toList();
    }

    public UserDTO findById(Long id) {
        return userMapper.toDTO(getUser(id));
    }

    public UserDTO create(UserDTO dto) {
        if (dto.passwordUser() == null || dto.passwordUser().length() < 8) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La contraseña debe tener al menos 8 caracteres");
        }
        if (userRepository.existsByEmailUser(dto.emailUser())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado");
        }
        User user = userMapper.toEntity(dto, getAlgorithm(dto.idAlgorithm()), getStyle(dto.idStyle()));
        user.setPasswordUser(passwordEncoder.encode(dto.passwordUser()));
        return userMapper.toDTO(userRepository.save(user));
    }

    public UserDTO update(Long id, UserDTO dto) {
        User user = getUser(id);
        userMapper.updateEntity(dto, user, getAlgorithm(dto.idAlgorithm()), getStyle(dto.idStyle()));
        return userMapper.toDTO(userRepository.save(user));
    }

    public void delete(Long id) {
        getUser(id);
        userRepository.deleteById(id);
    }

    private User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con id " + id));
    }

    // Opcionales: si no viene el id, devuelve null
    private Algorithm getAlgorithm(Long id) {
        if (id == null) return null;
        return algorithmRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Algoritmo no encontrado con id " + id));
    }

    private StyleLearning getStyle(Long id) {
        if (id == null) return null;
        return styleRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Estilo no encontrado con id " + id));
    }
}