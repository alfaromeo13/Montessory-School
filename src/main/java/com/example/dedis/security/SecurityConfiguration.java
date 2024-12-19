package com.example.dedis.security;

import com.example.dedis.security.components.UnauthorizedHttpEntryPoint;
import com.example.dedis.security.components.UserDetailsServiceImpl;
import com.example.dedis.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtFilter jwtFilter;
    private final UnauthorizedHttpEntryPoint unauthorizedHttpEntryPoint;

    @Bean // Manages the authentication process.
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    //Passwords are hashed using BCrypt encoding, ensuring they cannot be reversed or read in plaintext.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean // gazimo predefinisane vrijednosti sa nasim beanom.
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Enable this in production with proper configuration
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/admin/login").permitAll() // login available to anyone
                        .requestMatchers("/api/admin/**").authenticated() // Protect other admin endpoints
                        .anyRequest().permitAll())
                .exceptionHandling(authEntry -> authEntry.authenticationEntryPoint(unauthorizedHttpEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}