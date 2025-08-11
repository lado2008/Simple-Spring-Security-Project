package com.example.SpringSecurity.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UsernameAuthenticationFilter usernameAuthenticationFilter;
    private final JwtBasedAuthenticationFilter jwtBasedAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/bla").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(usernameAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtBasedAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
