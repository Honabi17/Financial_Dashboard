package com.luis.financial_backend.modules.auth.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;


@Service
public class JwtService {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration}")
    private long expiration;

    @Value("${security.jwt.refresh-expiration}")
    private long refreshExpiration;

    private Key signingKey;



    @PostConstruct
    private void init(){

        signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }



    public String generateToken(String username){

        return buildToken(username, expiration);
    }
    public String generateRefreshToken(String username){

        return buildToken(username, refreshExpiration);
    }

    private String buildToken(String subject, long durationMillis){
        Date now = new Date();
        Date expiry = new Date(now.getTime() + durationMillis);

        return
                Jwts.builder()
                        .setSubject(subject)
                        .setIssuedAt(now)
                        .setExpiration(expiry)
                        .signWith(signingKey, SignatureAlgorithm.HS256)
                        .compact();
    }



    public String extractUsername(String token){

        return extractClaim(token, Claims::getSubject);
    }
    public Date extractExpiration(String token){

        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver
    ){
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpiration(String token){

        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token, String username){
        final String tokenUsername = extractUsername(token);
        return (
                tokenUsername != null
                        &&
                        tokenUsername.equals(username)
                        &&
                        !isTokenExpiration(token)
                );
    }

}
