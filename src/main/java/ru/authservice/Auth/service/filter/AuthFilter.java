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
import ru.authservice.Auth.service.service.CookieService;
import ru.authservice.Auth.service.service.JwtTokenService;

import java.io.IOException;

@Slf4j
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService tokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CookieService cookieService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {
        var optionalJwt = cookieService.getJwtFromCookie(httpServletRequest);
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


}
