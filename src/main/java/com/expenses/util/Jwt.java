package com.expenses.util;

import com.expenses.constants.ErrorConstants;
import com.expenses.exception.ApplicationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.enterprise.context.ApplicationScoped;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class Jwt {

    @ConfigProperty(name="secret.key")
    String SECRET_KEY;

    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    public String generateToken(String email) {
        try {
            return Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                    .compact();
        } catch (Exception e) {
            throw new ApplicationException(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE, e);
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token);
            return true;
        } catch (SignatureException | ExpiredJwtException e) {
            return false;
        } catch (Exception e) {
            throw new ApplicationException(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE, e);
        }
    }

    public String validateTokenAndGetEmail(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (SignatureException | ExpiredJwtException e) {
            throw new ApplicationException(ErrorConstants.INVALID_TOKEN_CODE, ErrorConstants.INVALID_TOKEN_MESSAGE, e);
        } catch (Exception e) {
            throw new ApplicationException(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE, e);
        }
    }
}