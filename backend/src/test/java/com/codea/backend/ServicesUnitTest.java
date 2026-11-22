package com.codea.backend.services;

import com.codea.backend.dtos.AuthResponseDto;
import com.codea.backend.dtos.LessonDto;
import com.codea.backend.dtos.LoginRequestDto;
import com.codea.backend.dtos.UserRequestDto;
import com.codea.backend.dtos.UserResponseDto;
import com.codea.backend.entitys.Lesson;
import com.codea.backend.entitys.User;
import com.codea.backend.exceptions.BadRequestException;
import com.codea.backend.exceptions.ResourceNotFoundException;
import com.codea.backend.exceptions.UnauthorizedException;
import com.codea.backend.mappers.LessonMapper;
import com.codea.backend.mappers.UserMapper;
import com.codea.backend.repositorys.LessonRepository;
import com.codea.backend.repositorys.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ServicesUnitTest {

    // ===================== UserService (5 pruebas) =====================
    @Nested
    @ExtendWith(MockitoExtension.class)
    class UserServiceTests {

        @Mock UserRepository repository;
        @Mock UserMapper mapper;
        @Mock PasswordEncoder passwordEncoder;
        @InjectMocks UserService service;

        private UserRequestDto dto(String email, String password) {
            return UserRequestDto.builder()
                    .nameUser("Ana")
                    .emailUser(email)
                    .passwordUser(password)
                    .build();
        }

        @Test
        void create_encriptaPasswordYAsignaValoresPorDefecto() {
            UserRequestDto dto = dto("ana@mail.com", "1234");
            UserResponseDto response = UserResponseDto.builder().emailUser("ana@mail.com").build();

            when(repository.existsByEmailUser("ana@mail.com")).thenReturn(false);
            when(mapper.toEntity(dto)).thenReturn(new User());
            when(passwordEncoder.encode("1234")).thenReturn("HASH");
            when(repository.saveAndFlush(any(User.class))).thenAnswer(inv -> inv.getArgument(0));
            when(mapper.toResponse(any(User.class))).thenReturn(response);

            UserResponseDto result = service.create(dto);

            ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
            verify(repository).saveAndFlush(captor.capture());
            assertEquals("HASH", captor.getValue().getPasswordUser());
            assertEquals("USER", captor.getValue().getRolUser());
            assertEquals("ACTIVO", captor.getValue().getStatusUser());
            assertSame(response, result);
        }

        @Test
        void create_correoDuplicado_lanzaBadRequest() {
            UserRequestDto dto = dto("ana@mail.com", "1234");
            when(repository.existsByEmailUser("ana@mail.com")).thenReturn(true);

            BadRequestException ex = assertThrows(BadRequestException.class, () -> service.create(dto));

            assertEquals("Ya existe un usuario con ese correo", ex.getMessage());
            verify(repository, never()).saveAndFlush(any(User.class));
        }

        @Test
        void create_passwordVacia_lanzaBadRequest() {
            UserRequestDto dto = dto("ana@mail.com", "   ");

            BadRequestException ex = assertThrows(BadRequestException.class, () -> service.create(dto));

            assertEquals("La contraseña es obligatoria", ex.getMessage());
            verify(repository, never()).saveAndFlush(any(User.class));
        }

        @Test
        void update_correoDeOtroUsuario_lanzaBadRequest() {
            User existente = User.builder().idUser(1L).emailUser("ana@mail.com").build();
            UserRequestDto dto = dto("otro@mail.com", null);

            when(repository.findById(1L)).thenReturn(Optional.of(existente));
            when(repository.existsByEmailUser("otro@mail.com")).thenReturn(true);

            assertThrows(BadRequestException.class, () -> service.update(1L, dto));

            verify(repository, never()).saveAndFlush(any(User.class));
        }

        @Test
        void delete_usuarioInexistente_lanzaNotFound() {
            when(repository.existsById(99L)).thenReturn(false);

            ResourceNotFoundException ex =
                    assertThrows(ResourceNotFoundException.class, () -> service.delete(99L));

            assertEquals("Usuario no encontrado: 99", ex.getMessage());
            verify(repository, never()).deleteById(any());
        }
    }

    // ===================== AuthService (3 pruebas) =====================
    @Nested
    @ExtendWith(MockitoExtension.class)
    class AuthServiceTests {

        @Mock UserRepository userRepository;
        @Mock PasswordEncoder passwordEncoder;
        @Mock JwtService jwtService;
        @Mock UserMapper userMapper;
        @InjectMocks AuthService service;

        private LoginRequestDto login() {
            return LoginRequestDto.builder()
                    .emailUser("ana@mail.com")
                    .passwordUser("1234")
                    .build();
        }

        @Test
        void login_credencialesCorrectas_devuelveToken() {
            User user = User.builder().idUser(1L).emailUser("ana@mail.com").passwordUser("HASH").build();
            UserResponseDto userResponse = UserResponseDto.builder().idUser(1L).build();

            when(userRepository.findByEmailUser("ana@mail.com")).thenReturn(Optional.of(user));
            when(passwordEncoder.matches("1234", "HASH")).thenReturn(true);
            when(jwtService.generateToken(user)).thenReturn("jwt-token");
            when(jwtService.getExpirationSeconds()).thenReturn(3600L);
            when(userMapper.toResponse(user)).thenReturn(userResponse);

            AuthResponseDto result = service.login(login());

            assertEquals("jwt-token", result.getToken());
            assertEquals("Bearer", result.getTokenType());
            assertEquals(3600L, result.getExpiresIn());
            assertSame(userResponse, result.getUser());
        }

        @Test
        void login_usuarioNoExiste_lanzaUnauthorized() {
            when(userRepository.findByEmailUser("ana@mail.com")).thenReturn(Optional.empty());

            UnauthorizedException ex =
                    assertThrows(UnauthorizedException.class, () -> service.login(login()));

            assertEquals("Credenciales inválidas", ex.getMessage());
            verify(jwtService, never()).generateToken(any(User.class));
        }

        @Test
        void login_passwordIncorrecta_lanzaUnauthorized() {
            User user = User.builder().emailUser("ana@mail.com").passwordUser("HASH").build();

            when(userRepository.findByEmailUser("ana@mail.com")).thenReturn(Optional.of(user));
            when(passwordEncoder.matches("1234", "HASH")).thenReturn(false);

            assertThrows(UnauthorizedException.class, () -> service.login(login()));

            verify(jwtService, never()).generateToken(any(User.class));
        }
    }

    // ===================== LessonService (2 pruebas) =====================
    @Nested
    @ExtendWith(MockitoExtension.class)
    class LessonServiceTests {

        @Mock LessonRepository repository;
        @Mock LessonMapper mapper;
        @InjectMocks LessonService service;

        @Test
        void create_ignoraElIdEnviadoYGuarda() {
            LessonDto dto = LessonDto.builder().idLesson(99L).titleLesson("Fracciones").build();
            Lesson entity = new Lesson();
            entity.setIdLesson(99L);
            entity.setTitleLesson("Fracciones");
            LessonDto esperado = LessonDto.builder().idLesson(1L).titleLesson("Fracciones").build();

            when(mapper.toEntity(dto)).thenReturn(entity);
            when(repository.saveAndFlush(any(Lesson.class))).thenAnswer(inv -> inv.getArgument(0));
            when(mapper.toDto(any(Lesson.class))).thenReturn(esperado);

            LessonDto result = service.create(dto);

            ArgumentCaptor<Lesson> captor = ArgumentCaptor.forClass(Lesson.class);
            verify(repository).saveAndFlush(captor.capture());
            assertNull(captor.getValue().getIdLesson());
            assertSame(esperado, result);
        }

        @Test
        void findById_noExiste_lanzaNotFound() {
            when(repository.findById(5L)).thenReturn(Optional.empty());

            ResourceNotFoundException ex =
                    assertThrows(ResourceNotFoundException.class, () -> service.findById(5L));

            assertEquals("Lesson no encontrado: 5", ex.getMessage());
            verify(mapper, never()).toDto(any(Lesson.class));
        }
    }
}