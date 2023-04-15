package ru.authservice.Auth.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.authservice.Auth.service.dto.auth.request.LoginRequest;
import ru.authservice.Auth.service.dto.Token;
import ru.authservice.Auth.service.exception.RefreshTokenIsInvalid;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenService tokenProvider;
    private final UserService userService;
    private final CookieService cookieService;

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        var email = loginRequest.email();
        var user = userService.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email " + email));

        var responseHeaders = new HttpHeaders();
        var newAccessToken = tokenProvider.generateAccessToken(user.getEmail());
        var newRefreshToken = tokenProvider.generateRefreshToken(user.getEmail());
        addAccessTokenCookie(responseHeaders, newAccessToken);
        addRefreshTokenCookie(responseHeaders, newRefreshToken);
        return ResponseEntity.ok().headers(responseHeaders).build();
    }

    public ResponseEntity<?> refresh(String refreshToken) {
        var refreshTokenIsValid = tokenProvider.checkToken(refreshToken);
        if (!refreshTokenIsValid) {
            throw new RefreshTokenIsInvalid();
        }
        var currentUserEmail = tokenProvider.getSubjectFromToken(refreshToken);

        var newAccessToken = tokenProvider.generateAccessToken(currentUserEmail);
        var responseHeaders = new HttpHeaders();
        addAccessTokenCookie(responseHeaders, newAccessToken);
        return ResponseEntity.ok().headers(responseHeaders).build();
    }

    private void addAccessTokenCookie(HttpHeaders httpHeaders, Token token) {
        httpHeaders.add(HttpHeaders.SET_COOKIE,
                cookieService.createAccessTokenCookie(token)
                        .toString());
    }

    private void addRefreshTokenCookie(HttpHeaders httpHeaders, Token token) {
        httpHeaders.add(HttpHeaders.SET_COOKIE,
                cookieService.createRefreshTokenCookie(token)
                        .toString());
    }
}
