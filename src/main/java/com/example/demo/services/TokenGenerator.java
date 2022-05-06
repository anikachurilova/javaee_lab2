package com.example.demo.services;

import java.security.Key;
import com.example.demo.models.LoggedInUser;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenGenerator {

    private static final Key TOKEN = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(final LoggedInUser user) {
        return Jwts.builder().setSubject(user.getUsername()).signWith(TOKEN).compact();
    }

    public String getUsernameFromToken(final String s) {
        return Jwts.parserBuilder().setSigningKey(TOKEN).build().parseClaimsJws(s).getBody().getSubject();
    }
}