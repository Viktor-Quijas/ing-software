package com.codea.backend.services;

import com.codea.backend.entitys.User;
import com.codea.backend.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Autenticación con el correo (emailUser) y la contraseña encriptada con BCrypt. */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailUser(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));
        String role = user.getRolUser() == null || user.getRolUser().isBlank()
                ? "USER" : user.getRolUser().trim().toUpperCase();
        String authority = role.startsWith("ROLE_") ? role : "ROLE_" + role;
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmailUser())
                .password(user.getPasswordUser())
                .authorities(authority)
                .build();
    }
}
