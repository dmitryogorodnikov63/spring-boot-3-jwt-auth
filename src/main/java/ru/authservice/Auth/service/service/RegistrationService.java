package ru.authservice.Auth.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.authservice.Auth.service.dto.registration.request.RegistrationRequest;
import ru.authservice.Auth.service.entity.User;
import ru.authservice.Auth.service.enums.ERole;
import ru.authservice.Auth.service.repository.RoleRepository;
import ru.authservice.Auth.service.repository.UserRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean registration(RegistrationRequest request) {
        var adminRole = roleRepository.findByName(ERole.USER).orElseThrow();
        var newUser = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .email(request.email())
                .roles(Set.of(adminRole))
                .build();
        userRepository.save(newUser);
        return true;
    }
}
