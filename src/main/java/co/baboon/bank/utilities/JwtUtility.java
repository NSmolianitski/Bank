package co.baboon.bank.utilities;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.time.Duration;
import java.util.Date;

public class JwtUtility {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.lifetime}")
    private Duration lifetime;
    
    public String createJwtWithUserId(Integer id) {
        var issuedAt = new Date();
        var expiresAt = new Date(issuedAt.getTime() + lifetime.toMillis());
        
        return Jwts.builder()
                .issuedAt(issuedAt)
                .expiration(expiresAt)
                .subject(id.toString())
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), Jwts.SIG.HS256)
                .compact();
    }
    
    public String getJwtFromAuthorizationHeader(String header) {
        return header.substring(7);
    }
    
    public Integer getUserIdFromJwt(String jwt) {
        var claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
        
        return Integer.parseInt(claims.getSubject());
    }
}
