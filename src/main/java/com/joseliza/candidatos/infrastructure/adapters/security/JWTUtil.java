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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    /**
     * Genera un token JWT con roles.
     */
    public String generateToken(String username, List<String> roles) {
        Instant now = Instant.now();
        Key key = getSigningKey();

        return Jwts.builder()
                .claim("sub", username)
                .claim("roles", roles) // Incluye roles en el token
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(expiration)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Valida el token JWT.
     */
    public boolean validateToken(String token) {
        try {
            getParser().parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Extrae el nombre de usuario del token.
     */
    public String getUsername(String token) {
        return getParser()
                .parseClaimsJws(token)
                .getBody()
                .get("sub", String.class);
    }

    /**
     * Extrae roles del token y crea un objeto de autenticación.
     */
    public Authentication getAuthentication(String token) {
        String username = getUsername(token);

        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) getParser()
                .parseClaimsJws(token)
                .getBody()
                .get("roles");

        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    /**
     * Devuelve un parser JWT configurado con la clave secreta.
     */
    private JwtParser getParser() {
        Key key = getSigningKey();
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
    }

    /**
     * Decodifica y devuelve la clave secreta.
     */
    private Key getSigningKey() {
        byte[] keyBytes = io.jsonwebtoken.io.Decoders.BASE64.decode(secret);
        return io.jsonwebtoken.security.Keys.hmacShaKeyFor(keyBytes);
    }
}
