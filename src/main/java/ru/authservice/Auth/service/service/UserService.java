package ru.authservice.Auth.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.authservice.Auth.service.dto.CustomUserDetails;
import ru.authservice.Auth.service.dto.registration.request.ExistByEmailRequest;
import ru.authservice.Auth.service.dto.registration.request.ExistByUsernameRequest;
import ru.authservice.Auth.service.dto.registration.response.ExistByEmailResponse;
import ru.authservice.Auth.service.dto.registration.response.ExistByUsernameResponse;
import ru.authservice.Auth.service.entity.User;
import ru.authservice.Auth.service.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ExistByUsernameResponse existByUsername(ExistByUsernameRequest username) {
        return new ExistByUsernameResponse(userRepository.existsByUsername(username.username())) ;
    }

    public ExistByEmailResponse userIsExistByEmail(ExistByEmailRequest request) {
        return new ExistByEmailResponse(userRepository.existsByEmail(request.email()));
    }

    public User getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        User user = userRepository.findByEmail(customUserDetails.getUsername()).orElseThrow(() -> new IllegalArgumentException("User not found with email " + customUserDetails.getUsername()));
        return user;
    }


    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
