package ru.authservice.Auth.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.authservice.Auth.service.dto.CustomUserDetails;
import ru.authservice.Auth.service.dto.user.ProfileResponse;
import ru.authservice.Auth.service.repository.UserRepository;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/profile")
    public ProfileResponse test(Principal principal) {
        System.out.println("TEST");
        var username = userRepository.findByUsername(principal.getName());
        return new ProfileResponse(username.get().getUsername());
    }

}
