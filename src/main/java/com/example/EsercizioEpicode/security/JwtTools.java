package com.example.EsercizioEpicode.security;

import com.example.EsercizioEpicode.entities.Dipendente;
import com.example.EsercizioEpicode.exceptions.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@PropertySource("application.properties")
public class JwtTools {
    @Value("${spring.jwt.secret}")
    private String secret;
    @Value("${spring.jwt.expiration}")
    private String expiration;
    public String createToken(Dipendente dipendente){
        return "Bearer "+Jwts.builder().subject(dipendente.getUsername()).issuedAt(new Date(System.currentTimeMillis())).
                expiration(new Date(System.currentTimeMillis()+Long.parseLong(expiration))).
                signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();
    }
    public void validateToken(String token) throws UnauthorizedException {
        try{
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        }catch(Exception e){
            throw new UnauthorizedException(e.getMessage());
        }
    }
    public String extractUsernameFromToken(String token){
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }
}
