package com.leonardolima.parkrestapi.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtils {

    public static final String JWT_BEARER = "Bearer ";
    public static final String JWT_AUTHORIZATION = "Authorization";
    public static final String JWT_SECRET_KEY = "alçkfjaçlkj3çaçosdWJAOFlk34rnloadfjaoise";
    public static final long EXPIRE_DAYS = 0;
    public static final long EXPIRE_HOURS = 0;
    public static final long EXPIRE_MINUTES = 2; 

    private JwtUtils() {
    }

    private static Key generateKey() {
        return Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    private static Date toExpireDate(Date start) {
        LocalDateTime dateTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime end = dateTime.plusDays(EXPIRE_DAYS).plusHours(EXPIRE_HOURS).plusMinutes(EXPIRE_MINUTES);
        return Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static JwtToken createToken(String email, String role) {
        Date issueAt = new Date();
        Date limit = toExpireDate(issueAt);

        String token = Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setSubject(email)
            .setIssuedAt(issueAt)
            .setExpiration(limit)
            .signWith(generateKey(), SignatureAlgorithm.HS256)
            .claim("role", role)
            .compact();
        
        return new JwtToken(token);
    }

    private static Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                .setSigningKey(generateKey()).build()
                .parseClaimsJws(refactorToken(token)).getBody();
        } catch (JwtException e) {
            log.error("Token inválido", e.getMessage());
        }
        return null;
    }

    public static String getEmailFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }
    
    public static boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                .setSigningKey(generateKey()).build()
                .parseClaimsJws(refactorToken(token));
            return true;
        } catch (JwtException e) {
            log.error("Token inválido", e.getMessage());
        }
        return false;
    }

    private static String refactorToken(String token) {
        if (token.contains(JWT_BEARER)) {
            return token.substring(JWT_BEARER.length());
        }
        return token;
    }
}
