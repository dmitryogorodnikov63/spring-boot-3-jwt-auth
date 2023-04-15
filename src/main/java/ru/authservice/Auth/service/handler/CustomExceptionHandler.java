package ru.authservice.Auth.service.handler;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.authservice.Auth.service.exception.RefreshTokenIsInvalid;
import ru.authservice.Auth.service.exception.RoleNotFoundException;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler({RuntimeException.class,
            Exception.class,
            RoleNotFoundException.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    void handleOtherException(Exception e) {
        log.error(e.getMessage(), e);
    }

    @ExceptionHandler({AuthenticationException.class,
            AccessDeniedException.class,
            JwtException.class,
            MissingRequestCookieException.class,
            RefreshTokenIsInvalid.class
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    void handleAuthException(Exception e) {
        log.warn(e.getMessage());
    }


}
