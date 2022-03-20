package com.revature.service;

import com.revature.model.User;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class JWTService {
    private SecretKey key;

    public JWTService() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS384);
    }

    public String createJWT(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("user_id", user.getId())
                .claim("email", user.getEmail())
                .claim("user_role", user.getUserRole())
                .signWith(key)
                .compact();
    }

    public Jws<Claims> parseJwt(String jwt) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
        } catch(JwtException e) {
            e.printStackTrace();
            throw new UnauthorizedResponse("JWT was invalid");
        }

    }
}
