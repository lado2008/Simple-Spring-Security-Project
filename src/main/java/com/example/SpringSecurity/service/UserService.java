// service/UserService.java
package com.example.SpringSecurity.service;

import com.example.SpringSecurity.Security.CustomAuthentication;
import com.example.SpringSecurity.dto.LoginRequest;
import com.example.SpringSecurity.dto.LoginResponse;
import com.example.SpringSecurity.dto.RegisterRequest;
import com.example.SpringSecurity.entity.Role;
import com.example.SpringSecurity.entity.User;
import com.example.SpringSecurity.repository.RoleRepository;
import com.example.SpringSecurity.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String secret = "VERY-SECRET-KEY1231231233333333123";

    public void register(RegisterRequest dto) {
        Role role = roleRepository.findByName("ROLE_" + dto.getRole().toUpperCase())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .roles(Set.of(role))
                .build();
        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = Jwts.builder()
                .claim("username", user.getUsername())
                .claim("roles", user.getRoles().stream().map(Role::getName).toList())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();

        return new LoginResponse(token);
    }

    public Authentication authentication(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build().parseSignedClaims(token);
        Claims payload = claimsJws.getPayload();
        String username = payload.get("username", String.class);
        String role = payload.get("roles", java.util.List.class).get(0).toString();
        return new CustomAuthentication(username, role, true);
    }
}
