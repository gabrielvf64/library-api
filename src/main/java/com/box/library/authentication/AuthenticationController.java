package com.box.library.authentication;

import com.box.library.jwt.JwtUserDetailsService;
import com.box.library.request.UserLoginRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final JwtUserDetailsService jwtUserDetailsService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(JwtUserDetailsService jwtUserDetailsService,
                                    AuthenticationManager authenticationManager) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody @Valid UserLoginRequest loginRequest) {
        log.info("Autenticando com {}", loginRequest.username());

        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginRequest.username(), loginRequest.password());

            authenticationManager.authenticate(authenticationToken);

            var jwtToken = jwtUserDetailsService.getJwtToken(loginRequest.username());

            return ResponseEntity.ok(jwtToken);
        } catch (AuthenticationException e) {
            log.error("Erro ao autenticar", e);
        }
        return ResponseEntity.badRequest().body(new RuntimeException("Credenciais inválidas"));
    }
}
