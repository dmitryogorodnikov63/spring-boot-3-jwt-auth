package ru.authservice.Auth.service.exception;

import io.jsonwebtoken.JwtException;

public class TokenRefreshException extends JwtException {
    public TokenRefreshException() {
        super("Jwt refresh error");
    }
}
