package com.revature.service;

import com.revature.model.User;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class JWTService {
    private static JWTService INSTANCE;
    private SecretKey key;

    private JWTService() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS384);
    }

    public static JWTService getInstance() {
        if (INSTANCE == null) INSTANCE = new JWTService();
        return INSTANCE;
    }

    public String createJWT(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("user_id", user.getId())
                .claim("username", user.getUsername())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("email", user.getEmail())
                .claim("user_role", user.getUserRole())
                .signWith(key,SignatureAlgorithm.HS384)
                .compact();
    }

    public Jws<Claims> parseJwt(String jwt) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
        } catch(JwtException e) {
            throw new UnauthorizedResponse("JWT was invalid: " + e.getMessage());
        }

    }
}
