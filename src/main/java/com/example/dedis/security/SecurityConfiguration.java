package com.example.dedis.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UnauthorizedHttpEntryPoint unauthorizedHttpEntryPoint;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    //Passwords are hashed using BCrypt encoding, ensuring they cannot be reversed or read in plaintext.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    /*
    When using sessions, authentication is tied to a session ID stored in the browser as a cookie on client side.
    Browsers automatically include cookies in requests. The backend creates and manages sessions for authentication
    Upon successful authentication (user logs in using the form) the user's details are stored in the session.
    The session is managed server-side, allowing the user to access protected resources without re-authenticating until
    the session expires. I specified in application.yml session timeout time.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return  http
                .csrf(AbstractHttpConfigurer::disable) // todo: enable in production
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/admin/login").permitAll()
                        .requestMatchers("/api/admin/**").authenticated() // Restrict admin endpoints
                        .anyRequest().permitAll()) // Allow all other endpoints
                .exceptionHandling(authEntry -> authEntry.authenticationEntryPoint(unauthorizedHttpEntryPoint))
                .sessionManagement(session -> session.sessionFixation().migrateSession()) // Prevent session fixation
                .build();
    }
}