package br.com.unitechdesafio.infrastructure.config.security;

import br.com.unitechdesafio.infrastructure.dto.BearerToken;
import br.com.unitechdesafio.infrastructure.exception.InvalidBearerToken;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtSupport jwtSupport;
    private final ReactiveUserDetailsService users;

    public JwtAuthenticationManager(JwtSupport jwtSupport, ReactiveUserDetailsService users) {
        this.jwtSupport = jwtSupport;
        this.users = users;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication)
                .filter(auth -> auth instanceof BearerToken)
                .cast(BearerToken.class)
                .flatMap(this::validate)
                .onErrorMap(error -> new InvalidBearerToken(error.getMessage()));
    }

    private Mono<Authentication> validate(BearerToken token) {
        String username = jwtSupport.getUsername(token);
        return users.findByUsername(username)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("User not found")))
                .map(user -> {
                    if (jwtSupport.isValid(token, user)) {
                        return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
                    } else {
                        throw new IllegalArgumentException("Token is not valid.");
                    }
                });
    }
}