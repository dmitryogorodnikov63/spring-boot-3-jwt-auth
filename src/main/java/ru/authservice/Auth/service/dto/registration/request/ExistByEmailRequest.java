package ru.authservice.Auth.service.dto.registration.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ExistByEmailRequest(@NotBlank @Size(max = 50) String email){
}
