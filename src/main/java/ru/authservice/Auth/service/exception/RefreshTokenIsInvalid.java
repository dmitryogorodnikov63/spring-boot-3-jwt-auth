package ru.authservice.Auth.service.exception;

import io.jsonwebtoken.JwtException;

public class RefreshTokenIsInvalid extends JwtException {
    public RefreshTokenIsInvalid() {
        super("Refresh token is invalid");
    }
}
