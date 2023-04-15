package ru.authservice.Auth.service.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(@NotBlank(message = "Password cannot be empty") @Size(max = 120)
                           String password,
                          @NotBlank(message = "Email address cannot be empty")
                          @Size(max = 50)
                          @Email(message = "Please provide valid email address")
                          String email) {
}