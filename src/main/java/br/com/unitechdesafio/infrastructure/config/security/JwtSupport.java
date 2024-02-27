package br.com.unitechdesafio.infrastructure.config.security;

import br.com.unitechdesafio.business.domain.entity.UserDetailsEntity;
import br.com.unitechdesafio.infrastructure.dto.BearerToken;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtSupport {

    private final Key key = Keys.hmacShaKeyFor("tNO+KhVrTj3B4q0+SEwz/NSvZq7y577jOjvY4uPgAR4=".getBytes());
    private final JwtParser parser = Jwts.parserBuilder().setSigningKey(key).build();

    public BearerToken generate(UserDetailsEntity userDetails) {
        String token = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("name", userDetails.getCode())
                .claim("code", userDetails.getCode())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(15, ChronoUnit.MINUTES)))
                .signWith(key)
                .compact();

        return new BearerToken(token);
    }

    public String getUsername(BearerToken token) {
        return parser.parseClaimsJws(token.getValue()).getBody().getSubject();
    }

    public boolean isValid(BearerToken token, UserDetails user) {
        io.jsonwebtoken.Claims claims = parser.parseClaimsJws(token.getValue()).getBody();
        boolean unexpired = claims.getExpiration().after(Date.from(Instant.now()));

        return unexpired && claims.getSubject().equals(user.getUsername());
    }
}