package com.expenses.util;

import com.expenses.constants.ErrorConstants;
import com.expenses.exception.ApplicationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class Jwt {
    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    private static String getSecretKey() {
        String secretKey = System.getenv("SECRET_KEY");
        System.err.println("TESTE" + secretKey);
        if (secretKey == null || secretKey.isEmpty()) {
            throw new IllegalArgumentException("SECRET_KEY environment variable is not set or is empty.");
        }
        return secretKey;
    }

    public static String generateToken(String email) {
        try {
            return Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS512, getSecretKey())
                    .compact();
        } catch (Exception e) {
            throw new ApplicationException(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE, e);
        }
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(getSecretKey())
                .parseClaimsJws(token);
            return true;
        } catch (SignatureException | ExpiredJwtException e) {
            return false;
        } catch (Exception e) {
            throw new ApplicationException(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE, e);
        }
    }

    public static String validateTokenAndGetEmail(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (SignatureException | ExpiredJwtException e) {
            throw new ApplicationException(ErrorConstants.INVALID_TOKEN_CODE, ErrorConstants.INVALID_TOKEN_MESSAGE, e);
        } catch (Exception e) {
            throw new ApplicationException(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE, e);
        }
    }
}