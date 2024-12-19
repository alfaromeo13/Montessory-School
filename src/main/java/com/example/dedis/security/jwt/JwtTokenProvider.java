package com.example.dedis.security.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.validityInMinutes}")
    private long tokenValidityInMinutes;

    private static final String AUTH_KEY = "auth";

    // Generate SecretKey for signing JWT
    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    /**
     * Generate a JWT token based on the user's Authentication details.
     */
    public String generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return createToken(authorities, authentication, tokenValidityInMinutes);
    }

    /**
     * Create a JWT token with claims and expiration.
     */
    private String createToken(String authorities, Authentication authentication, long validityInMinutes) {
        return Jwts.builder()
                .subject(authentication.getName())  // Set subject (username)
                .claim(AUTH_KEY, authorities)       // Custom claim for roles
                .signWith(getKey())                 // Sign the token using SecretKey
                .expiration(new Date(System.currentTimeMillis() + validityInMinutes * 60_000))
                .compact();
    }

    /**
     * Retrieve Authentication (username + roles) from a valid token.
     */
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getKey())       // Set the signing key
                .build()
                .parseSignedClaims(token)   // Parse and validate the token
                .getPayload();              // Extract claims

        String username = claims.getSubject();
        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(AUTH_KEY).toString().split(",")) // [ROLE_ADMIN, ROLE_DEVELOPER]
                .map(SimpleGrantedAuthority::new)
                .toList();

        // Return an authentication token without a password (null for password)
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    /**
     * Validate a JWT token.
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser()
                    .verifyWith(getKey()) // Verify signature with key
                    .build()
                    .parseSignedClaims(authToken); // Parse token
            return true;
        } catch (JwtException e) {
            // Token is invalid, expired, or tampered with
            return false;
        }
    }
}