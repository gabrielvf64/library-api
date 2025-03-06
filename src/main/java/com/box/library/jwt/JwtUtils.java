package com.box.library.jwt;

import com.box.library.response.JwtTokenResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@Slf4j
public class JwtUtils {

    public static final String BEARER = "Bearer ";

    public static final String AUTHORIZATION = "Authorization";

    public static final String SECRET_KEY = "0123456789-0123456789-0123456789";

    public static final long EXPIRATION_DAYS = 0;

    public static final long EXPIRATION_HOURS = 0;

    public static final long EXPIRATION_MINUTES = 30;

    private JwtUtils() {
    }

    public static JwtTokenResponse createToken(String username, String role) {
        Date issuedAt = new Date();
        Date expireDate = getExpireDate(issuedAt);

        String token = Jwts.builder()
                .header().add("typ", "JWT")
                .and()
                .subject(username)  //TODO testar com id do usuario
                .issuedAt(issuedAt)
                .expiration(expireDate)
                .signWith(generateKey())
                .claim("role", role)
                .compact();

        return new JwtTokenResponse(token);
    }

    public static String getUserName(String token) {
        return Objects.requireNonNull(getClaims(token)).getSubject();
    }

    public static Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(generateKey())
                    .build()
                    .parseSignedClaims(removeBearer(token))
                    .getPayload();
        } catch (JwtException e) {
            log.error(String.format("Token inválido: %s", e.getMessage()));
        }
        return null;
    }

    public static boolean isNotValidToken(String token) {
        return !isValidToken(token);
    }

    public static boolean isValidToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(generateKey())
                    .build()
                    .parseSignedClaims(removeBearer(token));
            return true;
        } catch (JwtException e) {
            log.error(String.format("Token inválido: %s", e.getMessage()));
        }
        return false;
    }

    public static boolean doesNotStartsWithBearer(String token) {
        return !token.startsWith(JwtUtils.BEARER);
    }

    private static Date getExpireDate(Date startDate) {
        LocalDateTime startDateTime = startDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        LocalDateTime endDate = startDateTime.plusDays(EXPIRATION_DAYS)
                .plusHours(EXPIRATION_HOURS)
                .plusMinutes(EXPIRATION_MINUTES);

        return Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant());
    }

    private static SecretKey generateKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    private static String removeBearer(String token) {
        return token.contains(BEARER) ? token.substring(BEARER.length()) : token;
    }
}
