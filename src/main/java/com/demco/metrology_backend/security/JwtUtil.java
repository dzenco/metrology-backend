package com.demco.metrology_backend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;


@Service
public class JwtUtil {

    private static final Logger logger = Logger.getLogger(JwtUtil.class.getName());

    private KeyPair keyPair;

    public JwtUtil() {
        try {
            this.keyPair = generateECKeyPair();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize KeyPair", e);
        }
    }

    private KeyPair generateECKeyPair() throws Exception {
        try {
            return Keys.keyPairFor(SignatureAlgorithm.ES256);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to generate EC key pair", e);
            throw new Exception("Failed to generate EC key pair", e);
        }
    }




    public Date extractExpiration(String token){

        return extractClaims(token,Claims::getExpiration);
    }

    public <T> T extractClaims(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);

    }


    public Claims extractAllClaims(String token) {
        PublicKey publicKey = keyPair.getPublic();
        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }



    public String generateToken(String username, String role){
        Map<String,Object> claims = new HashMap<>();
        claims.put("role",role);
        return createToken(claims,username);
    }


    public String createToken(Map<String, Object> claims, String subject) {
        try {  logger.info("JWT successfully created");
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(subject)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 5)) // 5 heures
                   // .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 minutes
                    .signWith(keyPair.getPrivate(), SignatureAlgorithm.ES256)
                    .compact();
        }catch (Exception ex){

            logger.log(Level.SEVERE, "Failed to create JWT", ex);
            throw new RuntimeException("Failed to create JWT", ex);
        }

    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


   /* public Boolean validateToken(String token, UserDetails userDetails){

        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }*/
}