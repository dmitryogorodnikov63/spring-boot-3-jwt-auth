package ru.authservice.Auth.service.service;

import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import ru.authservice.Auth.service.dto.Token;
import ru.authservice.Auth.service.enums.CookieName;
import ru.authservice.Auth.service.util.SecurityCipher;

@Service
public class CookieService {

    public HttpCookie createAccessTokenCookie(Token token) {
        String encryptedToken = SecurityCipher.encrypt(token.getTokenValue());
        return ResponseCookie.from(CookieName.ACCESS_TOKEN_COOKIE_NAME.getName(), encryptedToken)
                .maxAge(token.getDuration())
                .httpOnly(true)
                .path("/")
                .build();
    }

    public HttpCookie createRefreshTokenCookie(Token token) {
        String encryptedToken = SecurityCipher.encrypt(token.getTokenValue());
        return ResponseCookie.from(CookieName.REFRESH_TOKEN_COOKIE_NAME.getName(), encryptedToken)
                .maxAge(token.getDuration())
                .httpOnly(true)
                .path("/")
                .build();
    }

    public HttpCookie createEmptyAccessToken() {
        return ResponseCookie.from(CookieName.ACCESS_TOKEN_COOKIE_NAME.getName())
                .httpOnly(true)
                .path("/")
                .build();
    }

    public HttpCookie createEmptyRefreshToken() {
        return ResponseCookie.from(CookieName.REFRESH_TOKEN_COOKIE_NAME.getName())
                .httpOnly(true)
                .path("/")
                .build();
    }
}
