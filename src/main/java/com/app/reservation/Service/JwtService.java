package com.app.reservation.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtService {
    private final UserService userService;
    @Value("${secretKey}")
    private String secretKey;

    @Value("${expiration}")
    private int expiration;

    public JwtService(UserService userService) {
        this.userService = userService;
    }

    private SecretKey generateSecretKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(Authentication authentication){
        return Jwts.builder().signWith(generateSecretKey()).subject(authentication.getName()).claim("ROLE_" ,userService.findByEmailEntity(authentication.getName()).getRole().toString()).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+expiration)).compact();
    }
    public String extractEmail(String token){
        return  Jwts.parser().verifyWith(generateSecretKey()).build().parseSignedClaims(token).getPayload().getSubject();

    }
    public boolean validateToken(String token){
        try{
            Jwts.parser().verifyWith(generateSecretKey()).build().parseSignedClaims(token);
            return true;
        }catch (Exception e){
            return false;
        }

    }
}
