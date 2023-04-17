package ru.authservice.Auth.service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.authservice.Auth.service.dto.registration.request.RegistrationRequest;
import ru.authservice.Auth.service.dto.registration.request.ExistByEmailRequest;
import ru.authservice.Auth.service.dto.registration.request.ExistByUsernameRequest;
import ru.authservice.Auth.service.dto.registration.response.ExistByEmailResponse;
import ru.authservice.Auth.service.dto.registration.response.ExistByUsernameResponse;
import ru.authservice.Auth.service.service.RegistrationService;
import ru.authservice.Auth.service.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

    private final RegistrationService registrationService;
    private final UserService userService;

    @PostMapping(value = "/user")
    public ResponseEntity<?> registrationUser(@Valid @RequestBody RegistrationRequest request) {
        return ResponseEntity.ok()
                .body(registrationService.registration(request));
    }

    @PostMapping(value = "/exist/username")
    private ExistByUsernameResponse existByUsername(@Valid @RequestBody ExistByUsernameRequest request) {
        return userService.existByUsername(request);
    }

    @PostMapping(value = "/exist/email")
    private ExistByEmailResponse existByEmail(@Valid @RequestBody ExistByEmailRequest request) {
        return userService.userIsExistByEmail(request);
    }
}
