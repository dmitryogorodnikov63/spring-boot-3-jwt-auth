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

        var newAccessToken = tokenProvider.generateAccessToken(user.getEmail());
        var newRefreshToken = tokenProvider.generateRefreshToken(user.getEmail());
        return ResponseEntity.ok()
                .headers(cookieService.generateAuthCookie(
                        newAccessToken,
                        newRefreshToken
                ))
                .build();
    }

    public ResponseEntity<?> refresh(String refreshToken) {
        var refreshTokenIsValid = tokenProvider.checkToken(refreshToken);
        if (!refreshTokenIsValid) {
            throw new RefreshTokenIsInvalid();
        }
        var currentUserEmail = tokenProvider.getSubjectFromToken(refreshToken);

        var newAccessToken = tokenProvider.generateAccessToken(currentUserEmail);
        var newRefreshToken = tokenProvider.generateRefreshToken(currentUserEmail);

        return ResponseEntity.ok()
                .headers(cookieService.generateAuthCookie(
                        newAccessToken,
                        newRefreshToken
                ))
                .build();
    }

    public ResponseEntity<?> logout() {
        return ResponseEntity.ok()
                .headers(cookieService.generateEmptyAuthCookie())
                .build();
    }



}
