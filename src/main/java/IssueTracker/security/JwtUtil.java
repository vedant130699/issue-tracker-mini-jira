package IssueTracker.security;

import IssueTracker.model.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
@Component
public class JwtUtil {
    private final String SECRET= "mysecretmysecretmysecretmysecretmysecretmysecretmysecretmysecret";
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(String username, Role role){
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60))
                .signWith(key)
                .compact();
    }
    //Extract username
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Extract role
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    //Validate token
    public boolean isTokenValid(String token) {
        return extractAllClaims(token).getExpiration().after(new Date());
    }


    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)   // same key you used for generating token
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

//    public String extractRole(String token) {
//        return extractAllClaims(token).get("role", String.class);
//    }
}
