package ru.authservice.Auth.service.dto;


import lombok.Getter;
import ru.authservice.Auth.service.enums.TokenType;

import java.time.LocalDateTime;


public record Token(@Getter TokenType tokenType,
                    @Getter String tokenValue,
                    @Getter Long duration,
                    @Getter LocalDateTime expiryDate) {
}