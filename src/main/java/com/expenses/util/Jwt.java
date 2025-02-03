package com.expenses.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class Jwt {
    // private static final String SECRET_KEY = System.getenv("SECRET_KEY"); // TODO This isnt working
    private static final String SECRET_KEY = "password";

    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    static {
        if (SECRET_KEY == null || SECRET_KEY.isEmpty()) {
            throw new IllegalArgumentException("SECRET_KEY environment variable is not set or is empty.");
        }
    }

    public static String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token);
            return true;
        } catch (SignatureException | ExpiredJwtException e) {
            return false;
        }
    }


    public static String validateTokenAndGetEmail(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}