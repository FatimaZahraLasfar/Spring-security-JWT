package org.fzl.tpjwtspring.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${wt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiraton}")
    private Long jwtExpiration;

    private SecretKey getSecretKey (){
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+jwtExpiration))
                .signWith(getSecretKey())
                .compact();
    }
    public String extractUserName(String token){
        return extractAllClaimsFromToken(token).getSubject();
    }
    public Claims extractAllClaimsFromToken(String token){
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getPayload();
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        String userName = extractUserName(token);
        return userDetails.getUsername().equalsIgnoreCase(userName);
    }
    public boolean isTokenExpired(String token){
        Date expiration =  extractAllClaimsFromToken(token).getExpiration();
        return expiration.before(new Date());
    }
}
