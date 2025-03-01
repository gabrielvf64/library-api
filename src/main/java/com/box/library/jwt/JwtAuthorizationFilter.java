package com.box.library.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private JwtUserDetailsService jwtUserDetailsService;

    public JwtAuthorizationFilter() {
    }

    public JwtAuthorizationFilter(JwtUserDetailsService jwtUserDetailsService) {
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        var token = request.getHeader(JwtUtils.AUTHORIZATION);

        if (token == null || JwtUtils.doesNotStartsWithBearer(token)) {
            log.info("Token nulo ou não começa com Bearer");
            filterChain.doFilter(request, response);
            return;
        }

        if (JwtUtils.isNotValidToken(token)) {
            log.warn("Token inválido ou expirado");
            filterChain.doFilter(request, response);
            return;
        }

        var userName = JwtUtils.getUserName(token);

        setAuthenticationToSpringContext(userName, request);

        filterChain.doFilter(request, response);
    }

    private void setAuthenticationToSpringContext(String userName, HttpServletRequest request) {
        var userDetails = jwtUserDetailsService.loadUserByUsername(userName);

        var authenticationToken = UsernamePasswordAuthenticationToken
                .authenticated(userDetails, null, userDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
