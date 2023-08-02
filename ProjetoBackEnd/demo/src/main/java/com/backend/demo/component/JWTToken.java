package com.backend.demo.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTToken {
    private final Key secret = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Replace with a strong secret key
    public String generateJwtToken(String username, String id)
    {
        Map<String, Object> object = new HashMap<String, Object>();
        object.put("username",username);
        object.put("id",id);
        return Jwts.builder()
                .setSubject(username)
                .setClaims(object)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000 )) // Token valid for 24 hours
                .signWith(secret)
                .compact();
    }

    public boolean validateJwtToken(String token) {
        boolean res = false;
        try
        {
            Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token);
            res = true;
        }
        catch (Exception ignored) {}
        return res;
    }

    public String getIdToken(String token) {
        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(secret)
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
        System.out.println(claims);
        return (String) claims.get("id");
    }

}
