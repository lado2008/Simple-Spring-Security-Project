package com.example.SpringSecurity.service;

import com.example.SpringSecurity.Security.CustomAuthentication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class UserService {

    private static final String secret = "VERY-SECRET-KEY1231231233333333123";

    public String login(String username, String password) {
        return Jwts.builder()
                .claim("username", username)
                .claim("role", "user")
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();
    }

    public Authentication authentication(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build().parseSignedClaims(token);
        Claims payload = claimsJws.getPayload();
        String username = payload.get("username", String.class);
        String role = payload.get("role", String.class);
        return new CustomAuthentication(role, username, true);
    }
}
