package com.box.library.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

    public static JwtTokenResponse createToken(String username, String role) {
        Date issuedAt = new Date();
        Date expireDate = toExpireDate(issuedAt);

//        String token = Jwts.builder()
//                .setHeaderParam("typ", "JWT")
//                .setSubject(username) //TODO testar com id do usuario
//                .setIssuedAt(issuedAt)
//                .setExpiration(expireDate)
//                .signWith(generateKey(), SignatureAlgorithm.HS256)
//                .claim("role", role)
//                .compact();

        String token = Jwts.builder()
                .header().add("typ", "JWT").and()
                .subject(username)  //TODO testar com id do usuario
                .issuedAt(issuedAt)
                .expiration(expireDate)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .claim("role", role)
                .compact();


        return new JwtTokenResponse(token);
    }
}
