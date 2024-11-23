package com.example.dedis.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UnauthorizedHttpEntryPoint unauthorizedHttpEntryPoint;

    /*
    We use a lightweight in-memory store provided by Spring Security to store admin authentication details during
    runtime. Passwords are hashed using BCrypt, ensuring they cannot be reversed or read in plaintext.
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin123"))
                .build();
        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
    When using sessions, authentication is tied to a session ID stored in the browser as a cookie on client side.
    Browsers automatically include cookies in requests. The backend creates and manages sessions for authentication,

    CSRF protection is essential to prevent unauthorized actions. it's enabled by default in Spring Security
    Upon successful authentication (user logs in using the form) the user's details are stored in the session.
    The session is managed server-side, allowing the user to access protected resources without re-authenticating until
    the session expires. I specified in application.yml session timeout time.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return  http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/admin/**").authenticated()
                        .anyRequest().permitAll())
                .formLogin(httpSecurityFormLoginConfigurer -> {}).build();
        //exceptionHandling(authEntry -> authEntry.authenticationEntryPoint(unauthorizedHttpEntryPoint))
    }
}