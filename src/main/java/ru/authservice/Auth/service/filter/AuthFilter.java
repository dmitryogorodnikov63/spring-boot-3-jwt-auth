package ru.authservice.Auth.service.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.authservice.Auth.service.service.JwtTokenService;
import ru.authservice.Auth.service.util.SecurityCipher;

import java.io.IOException;
import java.util.Optional;

import static ru.authservice.Auth.service.enums.CookieName.ACCESS_TOKEN_COOKIE_NAME;

@Slf4j
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService tokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {
        var optionalJwt = getJwtFromCookie(httpServletRequest);
        try {
            if (optionalJwt.isPresent()
                    && StringUtils.hasText(optionalJwt.get())
                    && tokenProvider.checkToken(optionalJwt.get())) {
                String email = tokenProvider.getSubjectFromToken(optionalJwt.get());
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private Optional<String> getJwtFromCookie(HttpServletRequest request) {
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
}
