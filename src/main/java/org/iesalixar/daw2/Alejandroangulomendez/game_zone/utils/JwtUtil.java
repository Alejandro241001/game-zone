package org.iesalixar.daw2.Alejandroangulomendez.game_zone.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Autowired
    private KeyPair jwtKeyPair;

    private static final long JWT_EXPIRATION = 3600000; // 1h

    /* ============================
            EXTRAER CLAIMS
       ============================ */

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Long extractUserId(String token) {
        return extractAllClaims(token).get("userId", Long.class);
    }

    public List<String> extractRoles(String token) {
        return extractAllClaims(token).get("roles", List.class);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(extractAllClaims(token));
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtKeyPair.getPublic()) // RSA PUBLIC KEY
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /* ============================
               CREAR TOKEN
       ============================ */

    public String generateToken(String username, Long userId, List<String> roles) {
        return Jwts.builder()
                .subject(username)
                .claim("userId", userId)
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(jwtKeyPair.getPrivate(), Jwts.SIG.RS256) // RSA PRIVATE KEY
                .compact();
    }

    /* ============================
               VALIDACIÓN
       ============================ */

    public boolean validateToken(String token, String username) {
        try {
            Claims claims = extractAllClaims(token);
            return username.equals(claims.getSubject()) && claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
