package ru.authservice.Auth.service.dto.registration.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ExistByUsernameRequest(@NotBlank @Size(max = 20) String username) {
}
