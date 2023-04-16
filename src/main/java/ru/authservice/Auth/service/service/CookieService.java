package ru.authservice.Auth.service.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import ru.authservice.Auth.service.dto.Token;
import ru.authservice.Auth.service.enums.CookieName;
import ru.authservice.Auth.service.util.SecurityCipher;

import java.util.Optional;

import static ru.authservice.Auth.service.enums.CookieName.ACCESS_TOKEN_COOKIE_NAME;

@Service
public class CookieService {

    public HttpHeaders generateAuthCookie(Token accessToken, Token refreshToken) {
        var headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE,
                createAccessTokenCookie(accessToken)
                        .toString());
        headers.add(HttpHeaders.SET_COOKIE,
                createRefreshTokenCookie(refreshToken)
                        .toString());
        return headers;
    }

    public HttpHeaders generateEmptyAuthCookie() {
        var headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE,
                createEmptyRefreshToken()
                        .toString());
        headers.add(HttpHeaders.SET_COOKIE,
                createEmptyAccessToken()
                        .toString());
        return headers;

    }

    public Optional<String> getJwtFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return Optional.empty();
        for (Cookie cookie : cookies) {
            if (ACCESS_TOKEN_COOKIE_NAME.getName().equals(cookie.getName())) {
                var accessToken = cookie.getValue();
                if (accessToken == null) return Optional.empty();

                return Optional.of(SecurityCipher.decrypt(accessToken));
            }
        }
        return Optional.empty();
    }

    private HttpCookie createAccessTokenCookie(Token token) {
        String encryptedToken = SecurityCipher.encrypt(token.getTokenValue());
        return ResponseCookie.from(CookieName.ACCESS_TOKEN_COOKIE_NAME.getName(), encryptedToken)
                .maxAge(token.getDuration())
                .httpOnly(true)
                .path("/")
                .build();
    }

    private HttpCookie createRefreshTokenCookie(Token token) {
        String encryptedToken = SecurityCipher.encrypt(token.getTokenValue());
        return ResponseCookie.from(CookieName.REFRESH_TOKEN_COOKIE_NAME.getName(), encryptedToken)
                .maxAge(token.getDuration())
                .httpOnly(true)
                .path("/")
                .build();
    }

    private HttpCookie createEmptyAccessToken() {
        return ResponseCookie.from(CookieName.ACCESS_TOKEN_COOKIE_NAME.getName())
                .httpOnly(true)
                .path("/")
                .build();
    }

    private HttpCookie createEmptyRefreshToken() {
        return ResponseCookie.from(CookieName.REFRESH_TOKEN_COOKIE_NAME.getName())
                .httpOnly(true)
                .path("/")
                .build();
    }
}
