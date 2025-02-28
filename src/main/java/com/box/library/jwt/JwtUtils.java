package com.box.library.jwt;

import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
public class JwtUtils {

    public static final String BEARER = "Bearer ";

    public static final String AUTHORIZATION = "Authorization";

    public static final String SECRET_KEY = "0123456789-0123456789-0123456789";

    public static final long EXPIRATION_DAYS = 0;

    public static final long EXPIRATION_HOURS = 0;

    public static final long EXPIRATION_MINUTES = 2;

    private JwtUtils() {
    }

    private static Key generateKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    private static Date toExpireDate(Date startDate) {
        LocalDateTime localDateTime = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endDate = localDateTime.plusDays(EXPIRATION_DAYS).plusHours(EXPIRATION_HOURS).plusMinutes(EXPIRATION_MINUTES);
        return Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant());
    }
}
