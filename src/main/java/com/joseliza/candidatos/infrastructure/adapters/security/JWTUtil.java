package com.joseliza.candidatos.infrastructure.adapters.security;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; 
import org.springframework.security.core.Authentication; 
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public Authentication getAuthentication(String token) {
        String username = getUsername(token);
        return new UsernamePasswordAuthenticationToken(
            username,
            null,
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    public String generateToken(String username) {
        Instant now = Instant.now();
        Key key = getSigningKey();

        return Jwts.builder()
                .claim("sub", username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(expiration)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            getParser().parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public String getUsername(String token) {
        return getParser()
                .parseClaimsJws(token)
                .getBody()
                .get("sub", String.class);
    }

    private JwtParser getParser() {
        Key key = getSigningKey();
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
    }

    private Key getSigningKey() {
        byte[] keyBytes = io.jsonwebtoken.io.Decoders.BASE64.decode(secret);
        return io.jsonwebtoken.security.Keys.hmacShaKeyFor(keyBytes);
    }
}
