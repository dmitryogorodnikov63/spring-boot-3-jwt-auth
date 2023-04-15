package ru.authservice.Auth.service.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CookieName {
    ACCESS_TOKEN_COOKIE_NAME("accessToken"),
    REFRESH_TOKEN_COOKIE_NAME("refreshToken");

    private final String name;
}
