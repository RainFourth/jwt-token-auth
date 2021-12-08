package com.rrain.jwttokenauth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


/*
    https://jwt.io/
    https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt
    https://github.com/jwtk/jjwt
 */
@Service
public class JwtUtils {
    /**
     * Based the secret key it is doing the signing
     */
    private final String secret = "jwt-secret";

    /**
     * Extracting username from the token
     * @param token
     * @return
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracting the expiry of the token
     * @param token
     * @return
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracting the claim based on the condition
     * @param <T>
     * @param token
     * @param claimsResolver
     * @return
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Generating token
     * @param username
     * @return
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, System.currentTimeMillis() + 1000 * 60 * 60 * 10);
    }
    public String generateToken(String username, long exp) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, exp);
    }


    private String createToken(Map<String, Object> claims, String subject, long exp) {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(exp))
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();
    }

    /**
     * Validating the token
     * @param token
     * @param userDetails
     * @return
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
